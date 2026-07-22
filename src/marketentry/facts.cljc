(ns marketentry.facts
  "Per-jurisdiction public-procurement market-entry regulatory catalog
  -- the G2-style spec-basis table the Market-Entry Compliance Governor
  checks every `:jurisdiction/assess` proposal against ('did the advisor
  cite an OFFICIAL public source for this jurisdiction's requirements,
  or did it invent one?').

  Brunei Darussalam's real market-entry surface (curl-verified
  2026-07-22, HTTP 200 on every `mofe.gov.bn`/`agc.gov.bn`/
  `labour.gov.bn` host this iteration fetched; HTML read via a
  tag-stripped `pandoc -f html -t plain` dump, the Companies Act /
  Business Names Act / Income Tax Act / Employment Act / Personal Data
  Protection Order PDFs via `pdftotext -layout` -- so every citation
  below is HIGH confidence primary text this iteration actually read,
  not a secondary summary, except where explicitly marked otherwise):

  - **Public procurement is run by the State Tender Board (STB), a
    division of the Ministry of Finance and Economy (MOFE), and --
    unlike Barbados's live Bonfire e-procurement SaaS portal -- this
    iteration could NOT find a self-service e-procurement/vendor-bidding
    portal for Brunei.** `mofe.gov.bn/procurement-process-workflow/`'s
    own text (fetched directly), under its own heading 'PUBLICATION OF
    PROCUREMENT INFORMATION', states verbatim: 'Tender notice is
    published in English and Malay in Pelita Brunei an official
    Government newspaper. Notice contain information such as procuring
    entity, description of products, service, works to be procured,
    tender opening and closing dates and venue for collection of tender
    documents.' This is a GAZETTE/NEWSPAPER-NOTICE regime, not a
    modern self-service portal -- directly corroborated by a REAL, live
    tender notice this iteration fetched
    (`mofe.gov.bn/2026/06/01/tq_mtic_egnc-pm-4-1-1-2026/`, 'NETWORK
    CENTRAL PROCUREMENT 3.0, PUSAT KEBANGSAAN E-KERAJAAN (EGNC),
    KEMENTERIAN PENGANGKUTAN DAN INFOKOMUNIKASI', published 1 June 2026
    & 13 June 2026, closing 11 August 2026, Tender Fee BND2,005.00,
    with a 'Download PDF File' link) -- a dated notice + payable tender
    fee + downloadable PDF, not an online bid-submission workflow. The
    'One Common Portal' (OCP, `ocp.mofe.gov.bn`) IS a real, live
    government e-service portal, but this iteration's own direct fetch
    of it returned only a client-rendered 'Loading...' shell (an honest
    access gap, the same JS-blocked-page discipline prior siblings
    apply) -- what this iteration could independently confirm about
    OCP's function (via two real, dated MOFE notices, see below) is
    that it is the front door for COMPANY/BUSINESS REGISTRATION and tax
    e-filing, not public tendering. This catalog's pre-existing
    `README.md` (written before this iteration's research) described
    Brunei's procurement channel as 'e-procurement / e-perolehan';
    this iteration did NOT find a distinctly-named 'e-perolehan' portal
    and narrows that claim honestly to what was actually confirmed:
    gazette/notice-based tendering via Pelita Brunei + mofe.gov.bn, with
    STB/Mini Tender Board threshold-gated approval.
  - **Procurement threshold ladder, read directly from
    `mofe.gov.bn/general-information/`'s own text**: Small Value
    Purchase up to BND$2,000 (direct purchase); Quotations above
    BND$2,000 up to BND$50,000 (minimum 3 quotations, Quotation
    Committee approves); Open Tender for all above BND$50,000; Selective
    Tender approved by the Mini Tender Board (above BND$50,000 to
    BND$500,000) or the State Tender Board (above BND$500,000); Limited
    Tender/Waiver of Normal Procedures (urgent/national-security/IP-
    restricted cases) approved by Mini Tender Board or STB depending on
    amount. Separately, individual ministries/departments may conduct
    their own procurement but 'if the value of goods, services or works
    more than BND250,000.00 they must refer to State Tender Board,
    Ministry of Finance and Economy first for approval' (own text, same
    page) -- this is a REFERRAL threshold, a different gate from the
    Selective-Tender/Waiver approval-tier thresholds above, not a
    contradiction between the two MOFE pages this iteration fetched.
  - `:owner-authority` is the State Tender Board, MOFE -- the same
    page's own text: 'All government purchases must comply with the
    Financial Regulations of 1983 which require fairness, openness,
    competitiveness, integrity and efficiency in the government
    procurement policies.' BUT MOFE's own site navigation (captured
    directly in the raw HTML of a dated MOFE notice this iteration
    fetched, `mofe.gov.bn/2025/10/10/notice_01102025_rocbn/`) shows,
    under Department > Treasury > Financial Regulation, a currently
    linked 'Peraturan-Peraturan Kewangan, 2022' -- this iteration could
    NOT independently fetch and read the full text of EITHER the 1983
    or the 2022 Financial Regulations document itself (no direct PDF
    link surfaced for either), so this catalog cites MOFE's own 1983
    reference as `:legal-basis` while explicitly flagging, rather than
    silently resolving, the 2022 reference as an honest, unconfirmed gap
    -- the same 'don't paper over an access gap' discipline Barbados's
    catalog applies to its Act's own unconfirmed commencement
    Proclamation.
  - **Business registration (ROCBN) and tax registration (Collector of
    Income Tax) are DIFFERENT DIVISIONS of the SAME ministry (MOFE) --
    a variant of the two-body finding Barbados/Antigua and Barbuda made
    across DIFFERENT ministries.** A real, dated MOFE notice this
    iteration fetched and read in full
    (`mofe.gov.bn/2025/10/10/notice_01102025_rocbn/`, 'Notice No.
    04/2025 -- Notice of Inactive Companies Strike-Off Initiative',
    1 October 2025) states verbatim: 'The Registry of Companies and
    Business Names Division (ROCBN) under the Ministry of Finance and
    Economy would like to inform companies registered under the
    Companies Act (Chapter 39)...'; the same notice cites, and this
    iteration independently verified in the Companies Act's own primary
    PDF text, ss.107/108 (Annual Returns filing duty), s.111 (Annual
    General Meeting duty) and s.287A (Registrar's power to strike off an
    inactive company from the Register after a 30-day cure period and a
    Government Gazette notice) -- a REAL, currently-active enforcement
    programme (effective 1 November 2025), not a dormant statute.
    SEPARATELY, the Revenue Division (also MOFE, per its own
    `div_revenue_aboutus/` page: established May 1950, the Permanent
    Secretary serves as Collector of Income Tax) administers the Income
    Tax Act (Chapter 35) via STARS (System for Tax Administration and
    Revenue Services -- name confirmed via MOFE's own FAQ page title and
    corroborated by a separate MOFE notice mentioning pre-2015 ROCBN
    registrations being 'automatically integrated' into STARS).
  - **`:foreign-company-registration-*` grounds this vertical's flagship
    governor check (see `marketentry.governor` /
    `marketentry.registry`) -- a company incorporated OUTSIDE Brunei
    Darussalam must lodge a defined document set with the Registrar of
    Companies BEFORE it establishes a place of business or commences to
    carry on business in Brunei, per the Companies Act (Chapter 39) Part
    9 (ss.298-307), read directly in the Act's own primary PDF text.**
    s.298: 'This Part applies to all companies incorporated outside
    Brunei Darussalam which... establish a place of business in Brunei
    Darussalam.' s.299(1): 'Every company incorporated outside Brunei
    Darussalam shall, BEFORE it establishes a place of business or
    commences to carry on business in Brunei Darussalam, lodge with the
    Registrar for registration' a certified incorporation certificate,
    its constitution, a director list, notice of its Brunei registered
    office, and a memorandum/power of attorney naming 2+ Brunei-resident
    individuals authorised to accept service of process on its behalf.
    s.306: 'If any company to which this Part applies fails to comply
    with any of the foregoing provisions of this Part, the company and
    every officer or agent of the company is guilty of an offence and
    liable on conviction to a fine of $1,000 or, in the case of a
    continuing offence, $25 for every day during which the default
    continues.' This is NOT a turnover-scaled formula (Bulgaria), not a
    flat statutory threshold (Albania), not a boolean registry-
    membership read (Azerbaijan/Armenia/Bolivia), not a 3-tier
    contract-value classification (Antigua and Barbuda), not a bid-
    evaluation price-adjustment recompute (Benin), not a struck-off
    company-registry legal-capacity boolean (Belize), and not a
    validity-WINDOW expiry recompute (Barbados, `registration-date + N
    years >= submission-date`) -- it is a pure ORDER/PRECEDENCE check
    with no arithmetic at all: does `part9-registration-date` fall on or
    before `business-commencement-date`? `marketentry.registry`
    implements this as a single plain ISO-8601 string `compare`, no
    date-math, the simplest check-shape this vertical family has
    produced yet, precisely because the statute's own trigger is
    'before', not a fixed validity period. It is also gated differently
    from every prior sibling's flagship: Part 9 is, BY THE ACT'S OWN
    s.298 SCOPE, a check that only applies to companies incorporated
    OUTSIDE Brunei -- a domestic Brunei company never has a Part 9
    obligation at all, so this catalog and the governor check both gate
    on `:foreign-company?` ground truth, an entity-scope gate rather
    than a value/date-window gate.
  - `rep-spec-basis` NOTE: this iteration could NOT independently fetch
    and read either the 1983 or 2022 Financial Regulations document's
    own text (see the honest-gap note above), and so could NOT confirm
    or deny whether Brunei's procurement framework has a personal-
    exclusion-grounds provision extending disqualification to a
    corporate supplier's own directors/officers (the kind Barbados's
    Public Procurement Act 2021 s.88(2) has, and BLZ/ATG's own catalogs
    explicitly searched for and did not find). BRN's `rep-spec-basis`
    is therefore nil, the same honest-nil this family's BLZ/ATG
    siblings return -- for a DIFFERENT, explicitly-stated reason (an
    access gap on the primary regulatory text, not a negative finding
    within primary text that was actually read).
  - **Brunei's tax regime is genuinely NOT a standard corporate-
    income-tax + VAT/GST model -- verified from the Income Tax Act
    (Chapter 35)'s OWN primary text, not assumed.** s.35(1)(f) (2024
    consolidated edition, read directly): 'the year of assessment 2015
    and subsequent years of assessment upon the chargeable income of
    every company, tax at the rate of 18.5 per cent, on every dollar of
    the chargeable income thereof' -- independently corroborated by
    MOFE's own live Corporate Tax FAQ page, which states verbatim: 'The
    corporate income tax rate shall be levied and paid for each year of
    assessment at the rate of 18.5% of the chargeable income of every
    company.' Schedule 1(a) of the Act, its own primary text: 'Tax
    shall be charged, levied and collected only in respect of the
    incomes of companies within the meaning of section 2, and the
    provisions of the Act shall not have effect in respect of incomes of
    any other persons or bodies of persons' -- i.e. Brunei genuinely has
    NO personal income tax, confirmed directly in the Act's own
    disapplication clause (s.34's nominal individual-rate schedule,
    Schedule 2, is disapplied by this same Schedule 1(a) and is blank in
    the Act's own printed Schedule 2 table). This iteration also checked
    the Attorney General's Chambers' own official Index to the Laws of
    Brunei Darussalam under both 'G' and 'S' (where a 'Goods and
    Services Tax Act' or 'Sales Tax Act'/'Service Tax Act' would
    alphabetically appear) and found NEITHER listed -- corroborating
    (though not, by itself, conclusively proving a negative) that
    Brunei has no general VAT/GST/sales-tax statute; only Stamp Duty
    (Chapter 34, ad valorem on specified instruments: transfers,
    leases, mortgages, share transfers) and Income Tax (Chapter 35)
    apply to ordinary commercial activity. Oil & gas E&P profits are
    taxed separately and far higher under the Income Tax (Petroleum)
    Act (Chapter 119) -- MOFE's own income-tax page states 55%, a figure
    this iteration did NOT independently re-verify in the Petroleum
    Act's own primary PDF text (lower-confidence tier, reported
    honestly rather than silently treated as equally primary as the
    18.5%/no-personal-tax findings above, which this iteration DID
    verify directly in the Income Tax Act's own PDF).

  Coverage is reported HONESTLY (see `coverage`): a jurisdiction not in
  this table has NO spec-basis, full stop -- the advisor must not
  fabricate one, and the governor holds if it tries.")

(def catalog
  "iso3 -> requirement map. `:required-evidence` mirrors the generic
  intake/portal-registration/filing evidence set; `:legal-basis` /
  `:owner-authority` / `:provenance` are the G2 citation the governor
  requires before any `:jurisdiction/assess` proposal can commit.
  `:foreign-company-registration-*` grounds this vertical's flagship
  governor check (`foreign-company-registration-late?` in
  `marketentry.registry`) -- a pure PRECEDENCE check (registration must
  fall on or before the declared business-commencement date), gated on
  `:foreign-company?` entity scope rather than a value/date-window gate
  -- genuinely different from every prior sibling's check shape (see
  namespace docstring). `:rep-owner-authority` etc. are NOT populated
  for BRN (nil `rep-spec-basis`) -- an honest access gap on the
  Financial Regulations text, not a negative finding, see docstring."
  {"BRN" {:name "Brunei Darussalam"
          :owner-authority "State Tender Board (STB), Ministry of Finance and Economy (MOFE) -- mofe.gov.bn's own 'General Information' page, own primary text"
          :legal-basis "Financial Regulations (Peraturan-Peraturan Kewangan) -- MOFE's own general-information page cites the 'Financial Regulations of 1983' as the compliance baseline for all government purchases ('require fairness, openness, competitiveness, integrity and efficiency'); MOFE's own site navigation (Department > Treasury > Financial Regulation) currently links a 'Peraturan-Peraturan Kewangan, 2022'. This iteration could NOT independently fetch/read the full text of either the 1983 or 2022 document -- an explicit, honest access gap, see namespace docstring"
          :national-spec "No live self-service e-procurement/vendor-bidding portal found. Tenders are published as dated notices (English + Malay) in Pelita Brunei, the official Government newspaper/gazette, per mofe.gov.bn's own 'PUBLICATION OF PROCUREMENT INFORMATION' text, corroborated by a real, live tender notice (published 1 June 2026, closing 11 August 2026, Tender Fee BND2,005.00, downloadable PDF). One Common Portal (OCP, ocp.mofe.gov.bn) is a real e-service portal but this iteration's direct fetch returned only a client-rendered loading shell; its confirmed function (per two dated MOFE notices) is company/business registration and tax e-filing, NOT public tendering -- see namespace docstring for why this narrows the pre-existing README's 'e-perolehan' phrasing"
          :provenance "https://www.mofe.gov.bn/general-information/ ; https://www.mofe.gov.bn/procurement-process-workflow/ ; https://www.mofe.gov.bn/2026/06/01/tq_mtic_egnc-pm-4-1-1-2026/ ; https://www.mofe.gov.bn/downloadable-forms-2/"
          :required-evidence ["Registry of Companies and Business Names (ROCBN) certificate of incorporation/registration (Companies Act, Chapter 39) or business-name registration (Business Names Act, Chapter 92)"
                              "Collector of Income Tax (Revenue Division, MOFE) registration confirmation, Income Tax Act Chapter 35"
                              "Companies Act (Chapter 39) Part 9 foreign-company registration record, lodged with the Registrar BEFORE establishing a place of business or commencing business in Brunei (foreign-incorporated operators only)"
                              "Sector-specific contractor/supplier registration where applicable (Ministry of Development for construction/architecture/engineering; Electrical Department for electrical goods/works; Authority for Info-communications Technology (AiTi) for ICT services/works)"
                              "Confirmation of authorized representative"]
          :corporate-number-owner-authority "Collector of Income Tax, Revenue Division, Ministry of Finance and Economy"
          :corporate-number-legal-basis "Income Tax Act, Chapter 35 (in force 31 December 1949, 2024 consolidated edition, own primary text): s.2 defines 'Collector' as 'the Collector of Income Tax appointed under section 3'; s.35(1)(f) fixes the corporate rate at 18.5 per cent for year of assessment 2015 onward; s.52(1) requires 'Every company... to furnish a return of income for a year of assessment by 30th June'; Schedule 1(a) confirms the Act 'shall not have effect in respect of incomes of any other persons or bodies of persons' -- i.e. no personal income tax. Revenue Division's own 'STARS' (System for Tax Administration and Revenue Services) is its e-filing platform"
          :corporate-number-provenance "https://www.agc.gov.bn/AGC%20Images/LAWS/ACT_PDF/I/CAP%2035%20INCOME%20TAX%20ACT.pdf ; https://www.mofe.gov.bn/div_revenue_aboutus/ ; https://www.mofe.gov.bn/div_revenue_faq_corporatetax/ ; https://www.mofe.gov.bn/div_revenue_typesoftaxes_incometax/"
          :business-registration-owner-authority "Registrar of Companies (Companies Act, Chapter 39) / Registrar of Business Names (Business Names Act, Chapter 92) -- both appointed by His Majesty the Sultan and Yang Di-Pertuan -- day-to-day administration by the Registry of Companies and Business Names Division (ROCBN), Ministry of Finance and Economy"
          :business-registration-legal-basis "Companies Act, Chapter 39 (Enactment No. 25 of 1956, in force 1 January 1957, 2021 Revised Edition, own primary text): s.288 'His Majesty the Sultan and Yang Di-Pertuan shall appoint fit and proper persons to be the Registrar of Companies...'; ss.107/108 (Annual Returns filing duty), s.111 (Annual General Meeting duty) and s.287A (strike-off of an inactive company after a 30-day cure period + Government Gazette notice) are actively enforced -- confirmed via ROCBN's own real, dated Notice No. 04/2025 (1 October 2025), effective 1 November 2025. Business Names Act, Chapter 92 (Act 2 of 1958, in force 1 March 1958, own primary text): s.3(1) 'His Majesty the Sultan and Yang Di-Pertuan shall appoint a fit and proper person to be the Registrar of Business Names'"
          :business-registration-provenance "https://www.mofe.gov.bn/2025/10/10/notice_01102025_rocbn/ ; https://www.agc.gov.bn/AGC%20Images/LAWS/ACT_PDF/C/CHAPTER%20039%20(2021).pdf ; https://www.agc.gov.bn/AGC%20Images/LAWS/ACT_PDF/B/CHAPTER%20092.pdf"
          :foreign-company-registration-owner-authority "Registrar of Companies, under the Companies Act (Chapter 39) Part 9"
          :foreign-company-registration-legal-basis "Companies Act, Chapter 39, Part 9 (ss.298-307, own primary text): s.298 -- Part 9 applies to companies incorporated outside Brunei Darussalam that establish a place of business or commence to carry on business in Brunei Darussalam; s.299(1) -- such a company SHALL, BEFORE it establishes a place of business or commences to carry on business in Brunei Darussalam, lodge with the Registrar a certified incorporation certificate, its constitution, a director list, notice of its Brunei registered office, and a memorandum/power of attorney naming 2 or more Brunei-resident individuals authorised to accept service of process on its behalf; s.306 -- non-compliance is an offence, the company and every officer or agent liable on conviction to a fine of $1,000, or $25 for every day of a continuing offence"
          :foreign-company-registration-provenance "https://www.agc.gov.bn/AGC%20Images/LAWS/ACT_PDF/C/CHAPTER%20039%20(2021).pdf"}
   "USA" {:name "United States"
          :owner-authority "U.S. General Services Administration (GSA) / SAM.gov"
          :legal-basis "Federal Acquisition Regulation (FAR); System for Award Management"
          :national-spec "SAM.gov entity registration + NAICS self-certification"
          :provenance "https://sam.gov/"
          :required-evidence ["EIN record"
                              "SAM.gov registration record"
                              "State business registration record"
                              "Authorized-representative record"]}
   "DEU" {:name "Germany"
          :owner-authority "Beschaffungsamt des BMI / e-Vergabe platforms"
          :legal-basis "Gesetz gegen Wettbewerbsbeschränkungen (GWB) / VgV"
          :national-spec "e-Vergabe supplier registration under EU procurement directives"
          :provenance "https://www.evergabe-online.de/"
          :required-evidence ["Handelsregister extract"
                              "e-Vergabe registration record"
                              "USt-IdNr record"
                              "Authorized-representative record"]}})

(defn spec-basis
  "The jurisdiction's requirement map, or nil -- nil means NO spec-basis,
  and the governor must hold any proposal that tries to assess or file
  on it."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report: how many of the requested jurisdictions actually
  have a spec-basis entry. Never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-brn R0: " (count catalog)
                 " jurisdictions seeded with an official spec-basis. "
                 "This is a starting catalog for market-entry navigation, "
                 "not a survey of all ~194 jurisdictions -- extend "
                 "`marketentry.facts/catalog`, never fabricate a "
                 "jurisdiction's requirements.")})))

(defn required-evidence-satisfied?
  "Does `submitted` (a set/coll of evidence keywords or strings) satisfy
  every evidence item listed for `iso3`? Missing spec-basis -> never
  satisfied."
  [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (let [need (count required-evidence)
          have (count (filter (set submitted) required-evidence))]
      (= need have))))

(defn evidence-checklist [iso3]
  (:required-evidence (spec-basis iso3) []))

(defn rep-spec-basis
  "The jurisdiction's representative-related requirement map, or nil when
  this catalog has no such regime. For BRN this is nil -- an honest
  ACCESS GAP (the 1983/2022 Financial Regulations text could not be
  independently fetched/read), not a negative finding within primary
  text actually read -- see the `catalog` docstring."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))

(defn corporate-number-spec-basis
  "The jurisdiction's corporate-number / tax-id regime (Collector of
  Income Tax via STARS, for BRN), or nil."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority
                       :corporate-number-legal-basis
                       :corporate-number-provenance]))))

(defn business-registration-spec-basis
  "The jurisdiction's business (state) registration regime, or nil.
  Brunei's business/company registration is administered by ROCBN, a
  DIFFERENT DIVISION of the SAME ministry (MOFE) as the tax registrar
  (`corporate-number-spec-basis`, Revenue Division/Collector of Income
  Tax) and the procurement regulator (`:owner-authority`, State Tender
  Board) -- see the namespace docstring's two-division finding."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:business-registration-owner-authority sb)
      (select-keys sb [:business-registration-owner-authority
                       :business-registration-legal-basis
                       :business-registration-provenance]))))

(defn foreign-company-registration-spec-basis
  "The jurisdiction's foreign-company pre-registration regime, or nil.
  For BRN this is HIGH confidence, grounded directly in the Companies
  Act, Chapter 39's own primary text (Part 9, ss.298-307) -- the
  flagship check this vertical adds (a pure PRECEDENCE check, see
  `marketentry.registry`) is grounded here, not copied from a sibling's
  citation."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:foreign-company-registration-owner-authority sb)
      (select-keys sb [:foreign-company-registration-owner-authority
                       :foreign-company-registration-legal-basis
                       :foreign-company-registration-provenance]))))
