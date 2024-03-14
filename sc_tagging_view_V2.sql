-- View: public.sc_tagging_view_v2

-- DROP VIEW public.sc_tagging_view_v2;

CREATE OR REPLACE VIEW public.sc_tagging_view_v2 AS 
 SELECT apd.acctno AS sc_acctno,
    apd.primary_holder AS acctno,
    sc.acct_name,
    scsl.post_date,
    scsl.balance,
    apd.account_code,
    coa.acct_title,
    sldf.trans_code,
    sldf.amount,
        CASE
            WHEN sldf.entry_type = 1 THEN 'Credit'::text
            WHEN sldf.entry_type = 0 THEN 'Debit'::text
            ELSE NULL::text
        END AS entry_type,
    meminf.holder_profile_id,
    loc.ou_description
   FROM coop_fin_acct_profile_dtl apd
     JOIN coop_fin_class_sc sc USING (acctno)
     JOIN coop_fin_sc_sl scsl USING (acctno)
     JOIN coop_fin_sc_sldf sldf USING (sc_sl_no)
     JOIN ( SELECT coop_associate.ou_code,
            coop_associate.holder_profile_id
           FROM coop_associate
          WHERE coop_associate.reg_mem_no IS NULL
        UNION
         SELECT coop_member.ou_code,
            coop_member.holder_profile_id
           FROM coop_member) meminf ON apd.primary_holder::text = meminf.holder_profile_id::text
     JOIN coop_fin_chart_of_accounts coa ON apd.account_code::text = coa.account_code::text
     JOIN ( SELECT coop_org_unit.ou_code,
            coop_org_unit.ou_description
           FROM coop_org_unit) loc USING (ou_code)
  WHERE apd.acctno::text ~~ 'SC%'::text;

ALTER TABLE public.sc_tagging_view_v2
  OWNER TO postgres;
