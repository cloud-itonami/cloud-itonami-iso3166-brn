(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest brn-has-spec-basis
  (let [sb (facts/spec-basis "BRN")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (some? (facts/corporate-number-spec-basis "BRN")))
    (is (some? (facts/business-registration-spec-basis "BRN")))
    (is (some? (facts/foreign-company-registration-spec-basis "BRN")))))

(deftest brn-rep-spec-basis-is-honest-nil
  (testing "BRN's rep-spec-basis is nil -- an honest ACCESS GAP on the Financial Regulations text (1983/2022), not a negative finding within primary text actually read"
    (is (nil? (facts/rep-spec-basis "BRN")))))

(deftest brn-business-registration-is-a-different-division-from-tax-and-procurement
  (testing "business/company registration (ROCBN) and tax registration (Collector of Income Tax) are DIFFERENT DIVISIONS of the SAME ministry (MOFE) -- see namespace docstring"
    (let [reg (facts/business-registration-spec-basis "BRN")
          tax (facts/corporate-number-spec-basis "BRN")]
      (is (some? reg))
      (is (some? tax))
      (is (not= (:business-registration-owner-authority reg)
                (:corporate-number-owner-authority tax))))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ")))
  (is (nil? (facts/business-registration-spec-basis "ATL")))
  (is (nil? (facts/foreign-company-registration-spec-basis "ATL"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "BRN")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "BRN" all)))
    (is (not (facts/required-evidence-satisfied? "BRN" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["BRN" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 2 (:covered c)))
    (is (= ["ATL"] (:missing-jurisdictions c)))))
