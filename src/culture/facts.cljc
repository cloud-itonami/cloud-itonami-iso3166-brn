(ns culture.facts
  "Country-level regional-culture catalog for Brunei Darussalam (BRN) --
  national dishes, protected products, beverages, crafts, festivals and
  heritage sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"BRN"
   [{:culture/id "brn.dish.ambuyat"
     :culture/name "Ambuyat"
     :culture/country "BRN"
     :culture/kind :dish
     :culture/summary "Starchy dish derived from the interior trunk of the sago palm; the national dish of Brunei, also popular in the Malaysian states of Sarawak and Sabah."
     :culture/url "https://en.wikipedia.org/wiki/Ambuyat"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "brn.dish.nasi-katok"
     :culture/name "Nasi katok"
     :culture/country "BRN"
     :culture/kind :dish
     :culture/summary "Rice dish originating from Brunei, traditionally steamed rice with fried chicken and spicy sambal sauce, wrapped in brown paper as an individual serving."
     :culture/url "https://en.wikipedia.org/wiki/Nasi_katok"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "brn.dish.kelupis"
     :culture/name "Kelupis"
     :culture/country "BRN"
     :culture/kind :dish
     :culture/summary "Traditional glutinous rice snack from East Malaysia and Brunei; in Brunei it has been recognised as a national cultural heritage."
     :culture/url "https://en.wikipedia.org/wiki/Kelupis"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "brn.dish.kuih-cincin"
     :culture/name "Kuih cincin"
     :culture/name-local "Cincin"
     :culture/country "BRN"
     :culture/kind :dish
     :culture/summary "Traditional ring-shaped kuih of the Bruneian Malay people in Brunei and the Malaysian state of Sabah."
     :culture/url "https://en.wikipedia.org/wiki/Cincin"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "brn.craft.songket"
     :culture/name "Songket"
     :culture/country "BRN"
     :culture/kind :craft
     :culture/summary "Hand-woven brocade fabric patterned with gold or silver threads, belonging to the textile traditions of Brunei, Indonesia and Malaysia; part of the ceremonial court dress of Bruneian royalty."
     :culture/url "https://en.wikipedia.org/wiki/Songket"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "brn.heritage.kampong-ayer"
     :culture/name "Kampong Ayer"
     :culture/country "BRN"
     :culture/kind :heritage
     :culture/summary "Historic stilt-house water settlement on the Brunei River in Bandar Seri Begawan, dating back at least to the 14th-century reign of Sultan Muhammad Shah."
     :culture/url "https://en.wikipedia.org/wiki/Kampong_Ayer"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "brn.heritage.omar-ali-saifuddien-mosque"
     :culture/name "Omar Ali Saifuddien Mosque"
     :culture/country "BRN"
     :culture/kind :heritage
     :culture/summary "State mosque in Bandar Seri Begawan completed in 1958, described as one of the biggest and most striking mosques in the Far East."
     :culture/url "https://en.wikipedia.org/wiki/Omar_Ali_Saifuddien_Mosque"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-brn culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "BRN"))
                 " BRN entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
