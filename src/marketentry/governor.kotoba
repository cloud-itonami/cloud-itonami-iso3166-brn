(ns marketentry.governor
  "Market-Entry Compliance Governor -- the independent compliance layer
  that earns the MarketEntry-LLM the right to commit. The LLM has no
  notion of Brunei Darussalam procurement law, whether a claimed
  engagement fee actually equals base + months x rate, whether the
  engagement's own declared Companies Act (Chapter 39) Part 9
  foreign-company registration was lodged BEFORE the engagement's own
  declared business-commencement date, whether Collector of Income Tax
  registration has been verified for a filing that requires it, or when
  a draft stops being a draft and becomes a real-world Pelita Brunei
  tender response / Part 9 lodgement, so this MUST be a separate system
  able to *reject* a proposal and fall back to HOLD.

  `:itonami.blueprint/governor` is `:market-entry-compliance-governor`
  (shared family keyword on blueprints).

  This blueprint's own text (docs/business-model.md Trust Controls:
  'any actual tender response or Part 9 lodgement submission requires
  Market-Entry Compliance Governor clearance and always escalates to
  human sign-off'; 'a false or fabricated regulatory-requirement claim
  is a HARD hold') names exactly the checks below.

  Six checks, in priority order, ALL HARD violations: a human
  approver CANNOT override them. The confidence/actuation gate is
  SOFT: it asks a human to look (low confidence / actuation), and the
  human may approve -- but see `marketentry.phase`: for `:stake
  :actuation/draft-filing`/`:actuation/submit-filing` NO phase ever
  allows auto-commit either. Two independent layers agree that
  actuation is always a human call.

    1. Spec-basis                  -- did the jurisdiction proposal cite
                                       an OFFICIAL source
                                       (`marketentry.facts`), or invent
                                       one?
    2. Evidence incomplete         -- for `:filing/draft`/
                                       `:filing/submit`, has the
                                       jurisdiction actually been
                                       assessed with a full evidence
                                       checklist on file?
    3. Foreign-company Part 9
       registration late           -- for `:filing/submit`, when the
                                       engagement declares
                                       `:foreign-company? true`,
                                       INDEPENDENTLY recompute whether
                                       the engagement's own declared
                                       `:part9-registration-date` falls
                                       on or before its own declared
                                       `:business-commencement-date`
                                       (Companies Act, Chapter 39
                                       s.299(1)), and HARD-hold if not.
                                       FLAGSHIP check for this
                                       jurisdiction -- a pure PRECEDENCE
                                       check (no date arithmetic at
                                       all), a check SHAPE genuinely
                                       different from every prior
                                       sibling's (turnover formula /
                                       flat threshold / boolean registry
                                       membership / 3-tier value class /
                                       bid-margin recompute / struck-off
                                       boolean / validity-window expiry
                                       recompute), and entity-SCOPE-gated
                                       (only companies incorporated
                                       outside Brunei have a Part 9
                                       obligation at all -- s.298) rather
                                       than value/date-window-gated. See
                                       `marketentry.facts` /
                                       `marketentry.registry`.
    4. Engagement fee mismatch     -- for `:filing/submit`,
                                       INDEPENDENTLY recompute whether
                                       the engagement's own `:claimed-
                                       fee` equals `base-fee +
                                       monthly-rate x monitoring-
                                       months` -- honest reapplication
                                       of the ground-truth-recompute
                                       discipline sibling actors use.
    5. Income Tax registration
       unverified                   -- for `:filing/submit`, when the
                                       engagement declares
                                       `:requires-income-tax-
                                       registration? true`,
                                       INDEPENDENTLY check
                                       `:income-tax-registered?`.
                                       CONDITIONAL on the engagement's
                                       own ground truth. Grounded in the
                                       Income Tax Act (Chapter 35) s.52
                                       annual-return duty owed to the
                                       Collector of Income Tax, Revenue
                                       Division, MOFE (see
                                       `marketentry.facts`).
    6. Confidence floor / actuation
       gate                          -- LLM confidence below threshold,
                                       OR the op is `:filing/draft`/
                                       `:filing/submit` (REAL acts)
                                       -> escalate.

  Two more guards, double-draft/double-submit prevention, are enforced
  off dedicated `:drafted?`/`:submitted?` facts (never a `:status`
  value)."
  (:require [marketentry.facts :as facts]
            [marketentry.registry :as registry]
            [marketentry.store :as store]))

(def confidence-floor 0.6)

(def high-stakes
  "Stakes grave enough to always require a human, even when clean.
  Drafting a real tender-response/Part-9-lodgement package and
  submitting it are the two real-world actuation events this actor
  performs."
  #{:actuation/draft-filing :actuation/submit-filing})

;; ----------------------------- checks -----------------------------

(defn- spec-basis-violations
  "A `:jurisdiction/assess` (or `:filing/draft`/`:filing/submit`)
  proposal with no spec-basis citation is a HARD violation -- never
  invent a jurisdiction's market-entry requirements."
  [{:keys [op]} proposal]
  (when (contains? #{:jurisdiction/assess :filing/draft :filing/submit} op)
    (let [value (:value proposal)]
      (when (or (empty? (:cites proposal))
                (and (contains? value :spec-basis) (nil? (:spec-basis value))))
        [{:rule :no-spec-basis
          :detail "公式spec-basisの引用が無い提案は法域要件として扱えない"}]))))

(defn- evidence-incomplete-violations
  "For `:filing/draft`/`:filing/submit`, the jurisdiction's required
  registration evidence must actually be satisfied."
  [{:keys [op subject]} st]
  (when (contains? #{:filing/draft :filing/submit} op)
    (let [e (store/engagement st subject)
          assessment (store/assessment-of st subject)]
      (when-not (and assessment
                     (facts/required-evidence-satisfied?
                      (:jurisdiction e) (:checklist assessment)))
        [{:rule :evidence-incomplete
          :detail "法域の必要書類(ROCBN登録/Collector of Income Tax登録/Part9外国法人登録/代理人確認等)が充足していない状態での提案"}]))))

(defn- foreign-company-late-registration-violations
  "For `:filing/submit`, when the engagement declares `:foreign-company?
  true`, INDEPENDENTLY recompute whether its own declared Companies Act
  (Chapter 39) Part 9 registration was lodged on or before its own
  declared business-commencement date -- the flagship check this
  vertical adds. Entity-scope-gated (a no-op for a domestic Brunei
  company): Part 9, by the Act's own s.298, only applies to companies
  incorporated outside Brunei Darussalam."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (registry/foreign-company-registration-late? e)
        [{:rule :foreign-company-registration-late
          :detail (str subject " のCompanies Act(Cap.39)Part9登録(登録日" (:part9-registration-date e)
                      ")は事業開始日(" (:business-commencement-date e)
                      ")より後 -- s.299(1)は事業開始/営業所設置の前の登録を義務付けており、"
                      "提出提案は進められない")}]))))

(defn- engagement-fee-mismatch-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own claimed fee equals base + months x rate."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when-not (registry/engagement-fee-matches-claim? e)
        [{:rule :engagement-fee-mismatch
          :detail (str subject " の申告手数料(" (:claimed-fee e)
                      ")が独立再計算値(" (registry/compute-engagement-fee e) ")と一致しない")}]))))

(defn- income-tax-registration-unverified-violations
  "For `:filing/submit`, when the engagement declares
  `:requires-income-tax-registration? true`, INDEPENDENTLY check
  `:income-tax-registered?` -- CONDITIONAL on the engagement's own
  ground truth. Grounded in the Income Tax Act (Chapter 35) s.52 annual
  return duty owed to the Collector of Income Tax, Revenue Division,
  Ministry of Finance and Economy."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (and (true? (:requires-income-tax-registration? e))
                 (not (true? (:income-tax-registered? e))))
        [{:rule :income-tax-registration-unverified
          :detail (str subject " はCollector of Income Tax(Income Tax Act Cap.35)への登録確認を要するが未確認 -- 提出提案は進められない")}]))))

(defn- already-drafted-violations
  "For `:filing/draft`, refuses to draft the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/draft)
    (when (store/engagement-already-drafted? st subject)
      [{:rule :already-drafted
        :detail (str subject " は既にドラフト済み")}])))

(defn- already-submitted-violations
  "For `:filing/submit`, refuses to submit the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (when (store/engagement-already-submitted? st subject)
      [{:rule :already-submitted
        :detail (str subject " は既に提出済み")}])))

(defn check
  "Censors a MarketEntry-LLM proposal against the governor rules.
  Returns {:ok? bool :violations [..] :confidence c :escalate? bool
  :high-stakes? bool :hard? bool}."
  [request _context proposal st]
  (let [hard (into []
                   (concat (spec-basis-violations request proposal)
                           (evidence-incomplete-violations request st)
                           (foreign-company-late-registration-violations request st)
                           (engagement-fee-mismatch-violations request st)
                           (income-tax-registration-unverified-violations request st)
                           (already-drafted-violations request st)
                           (already-submitted-violations request st)))
        conf (:confidence proposal 0.0)
        low? (< conf confidence-floor)
        stakes? (boolean (high-stakes (:stake proposal)))
        hard? (boolean (seq hard))]
    {:ok?          (and (not hard?) (not low?) (not stakes?))
     :violations   hard
     :confidence   conf
     :hard?        hard?
     :escalate?    (and (not hard?) (or low? stakes?))
     :high-stakes? stakes?}))

(defn hold-fact
  "The audit fact written when a proposal is rejected (HOLD)."
  [request context verdict]
  {:t          :governor-hold
   :op         (:op request)
   :actor      (:actor-id context)
   :subject    (:subject request)
   :disposition :hold
   :basis      (mapv :rule (:violations verdict))
   :violations (:violations verdict)
   :confidence (:confidence verdict)})
