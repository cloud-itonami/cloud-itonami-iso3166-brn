(ns statute.facts
  "General-law compliance catalog for Brunei Darussalam (BRN) -- extends
  this repo's existing `marketentry.facts` (public-procurement
  market-entry only, narrow scope) with a second, orthogonal catalog of
  statutes a company operating in this jurisdiction must generally
  track for compliance. Mirrors cloud-itonami-iso3166-aze/-bih/-jpn/
  -deu/-bgr/-blr/-bol/-blz/-atg/-brb's `statute.facts` (ADR-2607141700,
  cloud-itonami-compliance-fact-federation).

  Every entry below cites an OFFICIAL Brunei government source,
  curl-verified 2026-07-22, and every citation reflects PRIMARY TEXT
  this iteration actually fetched (`curl` + `pdftotext -layout`) and
  read in full, not a secondary summary:

  - **Companies Act, Chapter 39** -- downloaded in full as a PDF
    directly from the Attorney General's Chambers' (AGC) own official
    site (`agc.gov.bn/AGC%20Images/LAWS/ACT_PDF/C/CHAPTER%20039%20
    (2021).pdf`) and read via `pdftotext -layout`. HIGH confidence,
    primary text: Enactment No. 25 of 1956, 2021 Revised Edition, in
    force 1 January 1957; s.288 'His Majesty the Sultan and Yang
    Di-Pertuan shall appoint fit and proper persons to be the Registrar
    of Companies...'. AGC's own BRULAW index page ([C]) lists it as
    'CAP. 39', with subsidiary legislation available. This is the
    statute this repo's `marketentry.facts`
    `business-registration-spec-basis` / `foreign-company-registration-
    spec-basis` cites for ROCBN's Companies-Act functions, including
    the Part 9 foreign-company pre-registration mechanism this repo's
    market-entry flagship check is grounded in.
  - **Employment Act, Chapter 278** -- downloaded in full as a PDF
    directly from the Attorney General's Chambers' own official site
    (`agc.gov.bn/AGC%20Images/LAWS/ACT_PDF/E/CHAPTER%20278%20(2026).pdf`)
    and read via `pdftotext -layout`. HIGH confidence, primary text:
    originally S 37/2009 (Employment Order, 2009), consolidated as
    Chapter 278, 2026 Revised Edition; s.2 defines '\"Commissioner\"
    means the Commissioner of Labour appointed under section 3(1)'; s.3
    'His Majesty the Sultan and Yang Di-Pertuan may appoint a
    Commissioner of Labour... The Commissioner shall have the general
    responsibility of all matters to which this Act relates.' The
    Department of Labour's own website (`labour.gov.bn`, fetched
    directly) confirms it operates 'under the Ministry of Home
    Affairs' -- a GENUINELY DIFFERENT administering ministry from
    either the procurement regulator (State Tender Board, MOFE) or the
    company/tax registrars (ROCBN/Collector of Income Tax, both MOFE)
    this repo's `marketentry.facts` already documents.
  - **Personal Data Protection Order, 2025 (S 1/2025)** -- downloaded in
    full as a PDF directly from the Attorney General's Chambers' own
    official Gazette archive (`agc.gov.bn/AGC%20Images/LAWS/Gazette_PDF/
    2025/EN/S%201_2025%20[E].pdf`) and read via `pdftotext -layout`.
    HIGH confidence, primary text: 'Constitution of Brunei Darussalam
    (Order made under Article 83(3)) -- PERSONAL DATA PROTECTION ORDER,
    2025', gazetted 8th January 2025; s.2 defines '\"Authority\" means
    the Authority for Info-communications Technology Industry of Brunei
    Darussalam established by section 3 of the Authority for
    Info-communications Technology Industry of Brunei Darussalam Order,
    2001 (S 39/2001)'; Part 2 (Administration) s.4-s.6 and Part 3
    (Accountability for Personal Data) s.7 establish the compliance
    regime. AGC's own BRULAW index page ([P]) lists TWO separate
    commencement notifications for this Order -- S 10/2025 (8 January
    2025) and S 11/2025 (1 January 2026) -- consistent with s.1(2)'s own
    text allowing 'different dates... for provisions of this Order or
    for different purposes of the same provisions'; this iteration read
    the Order's own substantive text (S 1/2025) but did NOT separately
    fetch S 10/2025 or S 11/2025 to confirm exactly which sections each
    commencement notice activates -- an honest, explicit gap, reported
    rather than papered over.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries. `:statute/url` + `:statute/law-number`
  are the citation the governor requires before any compliance-fact
  proposal referencing this law can commit."
  {"BRN"
   [{:statute/id "brn.companies-act-cap39"
     :statute/title "Companies Act, Chapter 39 (Brunei Darussalam)"
     :statute/jurisdiction "BRN"
     :statute/kind :law
     :statute/law-number "Chapter 39, Enactment No. 25 of 1956, 2021 Revised Edition, in force 1 January 1957 -- administered by the Registrar of Companies, appointed under s.288 by His Majesty the Sultan and Yang Di-Pertuan, day-to-day via the Registry of Companies and Business Names Division (ROCBN), Ministry of Finance and Economy"
     :statute/url "https://www.agc.gov.bn/AGC%20Images/LAWS/ACT_PDF/C/CHAPTER%20039%20(2021).pdf"
     :statute/url-provenance :agc-official-site
     :statute/enacted-date "1957-01-01"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "brn.employment-act-cap278"
     :statute/title "Employment Act, Chapter 278 (Brunei Darussalam)"
     :statute/jurisdiction "BRN"
     :statute/kind :law
     :statute/law-number "Chapter 278, originally S 37/2009 (Employment Order, 2009), 2026 Revised Edition -- s.3 establishes the Commissioner of Labour's general-responsibility function, administered by the Department of Labour, Ministry of Home Affairs"
     :statute/url "https://www.agc.gov.bn/AGC%20Images/LAWS/ACT_PDF/E/CHAPTER%20278%20(2026).pdf"
     :statute/url-provenance :agc-official-site
     :statute/enacted-date "2009-09-03"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:labor :employment}}
    {:statute/id "brn.personal-data-protection-order-2025"
     :statute/title "Personal Data Protection Order, 2025 (Brunei Darussalam)"
     :statute/jurisdiction "BRN"
     :statute/kind :law
     :statute/law-number "S 1/2025 -- Order made under Article 83(3) of the Constitution of Brunei Darussalam, gazetted 8 January 2025; administered by the Authority for Info-communications Technology Industry of Brunei Darussalam (AiTi), per s.2's own 'Authority' definition. NOTE staged commencement per AGC's own index (S 10/2025, 8 Jan 2025; S 11/2025, 1 Jan 2026) -- this iteration did not separately fetch either commencement notice, see namespace docstring"
     :statute/url "https://www.agc.gov.bn/AGC%20Images/LAWS/Gazette_PDF/2025/EN/S%201_2025%20%5BE%5D.pdf"
     :statute/url-provenance :agc-official-gazette-archive
     :statute/enacted-date "2025-01-08"
     :statute/retrieved-at "2026-07-22"
     :statute/topic #{:data-protection :privacy}}]})

(defn spec-basis
  "The jurisdiction's statute vector, or nil -- nil means NO spec-basis
  for that jurisdiction yet."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report, same shape/discipline as `marketentry.facts/coverage`:
  never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-brn statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "BRN")) " BRN statutes seeded with an "
                 "official citation. Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic
  "Statutes for `iso3` tagged with `topic` (e.g. :labor, :data-protection)."
  [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
