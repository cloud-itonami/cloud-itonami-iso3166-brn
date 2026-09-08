(ns statute.facts-test
  (:require [kotoba.lang.text :as str]
            [clojure.test :refer [deftest is]]
            [statute.facts :as facts]))

(deftest brn-has-spec-basis
  (let [sb (facts/spec-basis "BRN")]
    (is (= 3 (count sb)))
    (is (every? #(str/starts-with? (:statute/url %) "https://") sb))
    (is (every? :statute/law-number sb))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["BRN" "JPN" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ATL" "JPN"] (:missing-jurisdictions c)))))

(deftest by-topic-filters
  (is (= ["brn.employment-act-cap278"]
         (mapv :statute/id (facts/by-topic "BRN" :labor))))
  (is (= ["brn.personal-data-protection-order-2025"]
         (mapv :statute/id (facts/by-topic "BRN" :data-protection))))
  (is (empty? (facts/by-topic "BRN" :environment)))
  (is (empty? (facts/by-topic "ATL" :labor))))
