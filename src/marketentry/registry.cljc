(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- every jurisdiction assigns its own format. This namespace
  does NOT invent one; it builds a jurisdiction-scoped sequence number
  and validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `foreign-company-registration-late?` is THIS vertical's own new
  ground-truth recompute, grounding BRN's flagship governor check
  (`marketentry.governor/foreign-company-late-registration-violations`):
  the Companies Act, Chapter 39 (own primary text) Part 9 s.299(1)
  requires a company incorporated OUTSIDE Brunei Darussalam to lodge its
  Part 9 registration with the Registrar of Companies BEFORE it
  establishes a place of business or commences to carry on business in
  Brunei Darussalam (see `marketentry.facts`). Dates are plain
  ISO-8601 \"YYYY-MM-DD\" strings -- deliberately no external date/
  calendar library and no host date API (`java.time` / `js/Date`), so
  the recompute is byte-identical on every `.cljc` target.

  This is a DIFFERENT check SHAPE from every prior sibling: not a
  turnover-scaled formula (Bulgaria), not a flat statutory threshold
  (Albania), not a boolean registry-membership read (Azerbaijan/
  Armenia/Bolivia), not a 3-tier contract-value classification (Antigua
  and Barbuda), not a bid-evaluation price-adjustment recompute (Benin),
  not a struck-off company-registry legal-capacity boolean (Belize), and
  not a validity-WINDOW expiry recompute (Barbados, `registration-date +
  N years >= submission-date`) -- it is a pure PRECEDENCE check with NO
  arithmetic at all: does `part9-registration-date` fall on or before
  `business-commencement-date`? Plain ISO-8601 string `compare` (which
  sorts zero-padded ISO-8601 dates in chronological order) is the
  entire recompute -- the simplest check-shape this vertical family has
  produced yet, because the statute's own trigger word is 'before', not
  a fixed validity period. It is also entity-scope-gated rather than
  value/date-window-gated: Part 9, by the Act's own s.298, only applies
  to companies incorporated OUTSIDE Brunei -- a domestic Brunei company
  never has this obligation at all, so this function (and the governor
  check built on it) is a no-op unless `:foreign-company?` is true.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real procurement or Pelita Brunei gazette system. It
  builds the RECORD an operator would keep, not the act of submitting a
  Pelita Brunei tender response or a Part 9 lodgement itself (that is
  `marketentry.operation`'s `:filing/submit`, always human-gated -- see
  README Actuation)."
  (:require [clojure.string :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(def ^:private money-scale
  "Sub-minor-unit scale used when comparing two money amounts: 1/10000 of
  a unit. Coarser than double representation error by many orders of
  magnitude, finer than any real currency's minor unit (2 decimals for
  most, 3 for KWD/BHD/OMR, 0 for JPY/KRW)."
  10000)

(defn- money=
  "Exact-at-money-precision equality for two amounts.

  `==` on raw doubles is NOT the right comparison for money. With
  whole-unit fees the two agree, but as soon as an amount carries
  cents the sum `base + rate x months` is routinely not the double
  nearest the true total, and a CORRECT claim compares false: measured
  on this exact shape, 40,989 of 327,060 cent-denominated combinations
  (12.5%) were rejected while being right, against 0 of 327,060 in
  whole units.

  Rounding both sides to `money-scale` before comparing removes the
  representation error while preserving every distinction money can
  actually carry."
  [x y]
  (and (number? x) (number? y)
       (= (Math/round (* money-scale (double x)))
          (Math/round (* money-scale (double y))))))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  ;; nil when any field is not a number: an un-recomputable engagement is
  ;; un-verifiable, which is neither `correct` nor a ClassCastException
  ;; thrown out of the caller.
  (when (and (number? base-fee) (number? monthly-rate) (number? monitoring-months))
    (+ (double base-fee)
       (* (double monthly-rate) (double monitoring-months)))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (money= claimed-fee (compute-engagement-fee engagement)))

(defn foreign-company-registration-late?
  "Is `engagement`'s own declared Companies Act Part 9 registration
  LATE relative to its own declared business-commencement date --
  i.e. does `part9-registration-date` fall STRICTLY AFTER
  `business-commencement-date`, in violation of s.299(1)'s 'before it
  establishes a place of business or commences to carry on business'
  requirement?

  A no-op (false) unless `:foreign-company?` is true -- Part 9, by the
  Companies Act's own s.298 scope, only applies to companies
  incorporated outside Brunei Darussalam. Missing either date, for a
  foreign company, is also never treated as late HERE (that is the
  `evidence-incomplete` check's job, upstream in the phase where an
  assessment must already exist) -- the same 'missing data is not a
  violation of THIS check' discipline `supplier-registration-expired?`
  uses in the Barbados sibling."
  [{:keys [foreign-company? part9-registration-date business-commencement-date]}]
  (boolean
   (when (true? foreign-company?)
     (when (and part9-registration-date business-commencement-date)
       (pos? (compare part9-registration-date business-commencement-date))))))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a Pelita Brunei tender
  response / Part 9 lodgement package. Pure function -- does not touch
  any real gazette, portal or Registrar system."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a tender
  response / Part 9 lodgement (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
