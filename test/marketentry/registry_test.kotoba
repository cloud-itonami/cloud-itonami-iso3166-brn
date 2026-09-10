(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "BRN" 0)
        s (registry/register-submit "eng-1" "BRN" 0)]
    (is (= "BRN-DFT-000000" (get d "draft_number")))
    (is (= "BRN-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "BRN" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))

(deftest foreign-company-registration-late-recompute
  (testing "Companies Act (Cap. 39) s.299(1) -- registration must fall on or before business commencement, pure precedence, no arithmetic"
    (is (true? (registry/foreign-company-registration-late?
                {:foreign-company? true
                 :part9-registration-date "2024-06-01"
                 :business-commencement-date "2024-03-01"}))
        "registered AFTER commencement -> late")
    (is (false? (registry/foreign-company-registration-late?
                 {:foreign-company? true
                  :part9-registration-date "2024-01-15"
                  :business-commencement-date "2024-03-01"}))
        "registered BEFORE commencement -> not late")
    (is (false? (registry/foreign-company-registration-late?
                 {:foreign-company? true
                  :part9-registration-date "2024-03-01"
                  :business-commencement-date "2024-03-01"}))
        "registered ON the same day as commencement -> not late (on-or-before)"))
  (testing "entity-scope-gated: a no-op (false) unless :foreign-company? is true, Part 9 s.298 only applies to companies incorporated outside Brunei"
    (is (false? (registry/foreign-company-registration-late?
                 {:foreign-company? false
                  :part9-registration-date "2024-06-01"
                  :business-commencement-date "2024-03-01"}))))
  (testing "missing either date -> never treated as late here (evidence-incomplete's job)"
    (is (false? (registry/foreign-company-registration-late?
                 {:foreign-company? true :business-commencement-date "2024-03-01"})))
    (is (false? (registry/foreign-company-registration-late?
                 {:foreign-company? true :part9-registration-date "2024-06-01"})))
    (is (false? (registry/foreign-company-registration-late? {:foreign-company? true})))))

;; ---------------------------------------------------------------------------
;; Money is compared at money precision, not at double precision
;; ---------------------------------------------------------------------------

(deftest whole-unit-fees-were-already-correct-and-stay-correct
  (testing "the seeded shape: base + rate x months in whole currency units"
    (is (registry/engagement-fee-matches-claim?
         {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12
           :claimed-fee 860000.0}))))

(deftest cent-denominated-fees-are-no-longer-rejected-while-correct
  (testing "`(== (double claimed) (+ (double base) (* (double rate) (double months))))`
            rejected CORRECT totals once an amount carried cents -- 40,989 of
            327,060 combinations (12.5%), against 0 of 327,060 in whole units"
    (let [bad (for [m (range 1 37)
                    bc (range 10000 90000 2100)
                    rc (range 500 6000 210)
                    :let [truth (/ (+ bc (* rc m)) 100.0)]
                    :when (not (registry/engagement-fee-matches-claim?
                                {:base-fee (/ bc 100.0) :monthly-rate (/ rc 100.0)
                                  :monitoring-months m :claimed-fee truth}))]
                [m (/ bc 100.0) (/ rc 100.0) truth])]
      (is (empty? bad) (str "false rejections: " (count bad) " e.g. " (first bad))))))

(deftest a-genuinely-wrong-fee-is-still-caught
  (testing "rounding to money precision must not blunt the check"
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12
                :claimed-fee 860000.01})))
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12
                :claimed-fee 859999.99})))))

(deftest an-unverifiable-fee-never-matches
  (testing "un-verifiable is not the same as correct, and not a crash"
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12})))
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee "500000" :monthly-rate 30000 :monitoring-months 12
                :claimed-fee 860000.0})))
    (is (nil? (registry/compute-engagement-fee {:base-fee 500000 :monthly-rate 30000})))))
