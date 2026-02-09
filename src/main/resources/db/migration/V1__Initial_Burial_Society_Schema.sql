-- =============================================================================
-- 1. SCHEMA INITIALIZATION
-- =============================================================================
CREATE SCHEMA IF NOT EXISTS member;
CREATE SCHEMA IF NOT EXISTS billing;
CREATE SCHEMA IF NOT EXISTS claims;
CREATE SCHEMA IF NOT EXISTS partner;
CREATE SCHEMA IF NOT EXISTS treasury;
CREATE SCHEMA IF NOT EXISTS audit;

-- =============================================================================
-- 2. MEMBER SCHEMA
-- =============================================================================

CREATE TABLE member.members (
                                id BIGSERIAL PRIMARY KEY,
                                case_number VARCHAR(255),
                                created_by VARCHAR(255),
                                creation_date TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE member.personal_details (
                                         id BIGSERIAL PRIMARY KEY,
                                         member_id BIGINT NOT NULL UNIQUE REFERENCES member.members(id) ON DELETE CASCADE,
                                         firstname VARCHAR(255) NOT NULL,
                                         lastname VARCHAR(255) NOT NULL,
                                         middlename VARCHAR(255),
                                         maiden_name VARCHAR(255),
                                         id_number VARCHAR(255) UNIQUE,
                                         identification_type VARCHAR(255),
                                         date_of_birth DATE,
                                         gender VARCHAR(50),
                                         title VARCHAR(50),
                                         nationality VARCHAR(255),
                                         citizenship VARCHAR(255),
                                         marital_status VARCHAR(50),
                                         religion VARCHAR(100),
                                         race VARCHAR(100),
                                         number_of_dependents INTEGER,
                                         number_of_other_dependents INTEGER,
                                         passport_number VARCHAR(255),
                                         passport_expiry_date DATE,
                                         driver_license_number VARCHAR(255),
                                         birth_district VARCHAR(255),
                                         usa_resident VARCHAR(10),
                                         usa_citizen VARCHAR(10),
                                         usa_green_card_holder VARCHAR(10),
                                         highest_level_of_education VARCHAR(255),
                                         ssr_number VARCHAR(255)
);

CREATE TABLE member.contact_details (
                                        id BIGSERIAL PRIMARY KEY,
                                        member_id BIGINT NOT NULL UNIQUE REFERENCES member.members(id) ON DELETE CASCADE,
                                        email VARCHAR(255) UNIQUE,
                                        phone_number VARCHAR(50) UNIQUE,
                                        telephone_number VARCHAR(50),
                                        facebook VARCHAR(255),
                                        twitter VARCHAR(255),
                                        linkedin VARCHAR(255),
                                        skype VARCHAR(255)
);

CREATE TABLE member.address_details (
                                        id BIGSERIAL PRIMARY KEY,
                                        member_id BIGINT NOT NULL REFERENCES member.members(id) ON DELETE CASCADE,
                                        address_type VARCHAR(50),
                                        address_line_1 VARCHAR(255),
                                        address_line_2 VARCHAR(255),
                                        street_name VARCHAR(255),
                                        street_number VARCHAR(50),
                                        suburb VARCHAR(255),
                                        city VARCHAR(255),
                                        country VARCHAR(255),
                                        postal_code VARCHAR(50),
                                        period_of_residence_years VARCHAR(50),
                                        period_of_residence_months VARCHAR(50),
                                        value_of_property VARCHAR(255),
                                        monthly_rental_amount VARCHAR(255),
                                        property_density VARCHAR(255)
);

CREATE TABLE member.employment_details (
                                           id BIGSERIAL PRIMARY KEY,
                                           member_id BIGINT NOT NULL UNIQUE REFERENCES member.members(id) ON DELETE CASCADE,
                                           employer_name VARCHAR(255),
                                           job_title VARCHAR(255),
                                           industry VARCHAR(255),
                                           industry_sub_category VARCHAR(255),
                                           monthly_gross_income DECIMAL(19, 2),
                                           monthly_net_income DECIMAL(19, 2),
                                           salary_currency VARCHAR(10),
                                           employment_type VARCHAR(100),
                                           employer_type VARCHAR(100),
                                           employment_date DATE,
                                           employment_end_date DATE,
                                           previous_employer_name VARCHAR(255),
                                           phone_number VARCHAR(50),
                                           telephone_number VARCHAR(50),
                                           email VARCHAR(255),
                                           address VARCHAR(255),
                                           customer_risk_status VARCHAR(50)
);

CREATE TABLE member.citizenship (
                                    id BIGSERIAL PRIMARY KEY,
                                    member_id BIGINT NOT NULL UNIQUE REFERENCES member.members(id) ON DELETE CASCADE,
                                    country VARCHAR(255),
                                    status VARCHAR(50),
                                    date_of_acquisition DATE
);

CREATE TABLE member.membership_plans (
                                         id BIGSERIAL PRIMARY KEY,
                                         member_id BIGINT NOT NULL UNIQUE REFERENCES member.members(id) ON DELETE CASCADE,
                                         plan_name VARCHAR(255),
                                         plan_identifier VARCHAR(255),
                                         total_price DECIMAL(19, 2)
);

CREATE TABLE member.plan_add_ons (
                                     id BIGSERIAL PRIMARY KEY,
                                     membership_plan_id BIGINT NOT NULL REFERENCES member.membership_plans(id) ON DELETE CASCADE,
                                     name VARCHAR(255),
                                     price DECIMAL(19, 2)
);

CREATE TABLE member.source_of_funds (
                                        id BIGSERIAL PRIMARY KEY,
                                        employment_details_id BIGINT NOT NULL REFERENCES member.employment_details(id) ON DELETE CASCADE,
                                        source VARCHAR(255),
                                        currency VARCHAR(10),
                                        amount DECIMAL(19, 2)
);

CREATE TABLE member.related_parties (
                                        id BIGSERIAL PRIMARY KEY,
                                        member_id BIGINT NOT NULL REFERENCES member.members(id) ON DELETE CASCADE,
                                        relationship_type VARCHAR(100),
                                        firstname VARCHAR(255),
                                        lastname VARCHAR(255),
                                        same_address BOOLEAN,
                                        same_employer BOOLEAN,
                                        is_existing_customer VARCHAR(50)
);

-- UPDATED: Changed content_base64 to OID to match @Lob annotation
CREATE TABLE member.document_metadata (
                                          id BIGSERIAL PRIMARY KEY,
                                          member_id BIGINT NOT NULL REFERENCES member.members(id) ON DELETE CASCADE,
                                          document_type VARCHAR(255),
                                          file_name VARCHAR(255),
                                          file_path VARCHAR(255),
                                          content_base64 OID,
                                          upload_date TIMESTAMPTZ
);

CREATE TABLE member.business_rules (
                                       id BIGSERIAL PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
                                       condition VARCHAR(1000) NOT NULL,
                                       output VARCHAR(1000) NOT NULL,
                                       effective_date DATE,
                                       status VARCHAR(50) NOT NULL
);

CREATE TABLE member.member_preferences (
                                           id BIGSERIAL PRIMARY KEY,
                                           member_id BIGINT NOT NULL UNIQUE REFERENCES member.members(id) ON DELETE CASCADE,
                                           preferred_communication_method VARCHAR(255),
                                           preferred_communication_for_reminders VARCHAR(255)
);

CREATE TABLE member.member_languages (
                                         preference_id BIGINT NOT NULL REFERENCES member.member_preferences(id) ON DELETE CASCADE,
                                         language VARCHAR(100)
);

CREATE TABLE member.member_hobbies (
                                       preference_id BIGINT NOT NULL REFERENCES member.member_preferences(id) ON DELETE CASCADE,
                                       hobby VARCHAR(100)
);

CREATE TABLE member.product_plans (
                                      id BIGSERIAL PRIMARY KEY,
                                      name VARCHAR(255) NOT NULL,
                                      premium DECIMAL(19, 2) NOT NULL,
                                      benefit_limit DECIMAL(19, 2) NOT NULL,
                                      waiting_period VARCHAR(255),
                                      status VARCHAR(50) NOT NULL,
                                      grace_period_days INTEGER,
                                      lapse_threshold_months INTEGER
);

CREATE TABLE member.system_users (
                                     id BIGSERIAL PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
                                     email VARCHAR(255) NOT NULL UNIQUE,
                                     role VARCHAR(100) NOT NULL,
                                     mfa_enabled BOOLEAN DEFAULT FALSE,
                                     last_login TIMESTAMP,
                                     password_hash VARCHAR(500) NOT NULL,
                                     status VARCHAR(50) NOT NULL
);

-- =============================================================================
-- 3. BILLING SCHEMA
-- =============================================================================

CREATE TABLE billing.billing_accounts (
                                          id BIGSERIAL PRIMARY KEY,
                                          member_id BIGINT NOT NULL UNIQUE REFERENCES member.members(id),
                                          current_balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
                                          billing_frequency VARCHAR(255) NOT NULL,
                                          base_contribution_amount DECIMAL(19, 2) NOT NULL,
                                          grace_period_days INTEGER,
                                          arrears_policy VARCHAR(255),
                                          next_billing_date DATE,
                                          last_payment_date DATE,
                                          account_status VARCHAR(255) NOT NULL
);

CREATE TABLE billing.invoices (
                                  id BIGSERIAL PRIMARY KEY,
                                  billing_account_id BIGINT NOT NULL REFERENCES billing.billing_accounts(id),
                                  invoice_number VARCHAR(255) NOT NULL UNIQUE,
                                  invoice_date DATE NOT NULL,
                                  due_date DATE NOT NULL,
                                  period_start_date DATE NOT NULL,
                                  period_end_date DATE NOT NULL,
                                  amount DECIMAL(19, 2) NOT NULL,
                                  amount_paid DECIMAL(19, 2) NOT NULL,
                                  status VARCHAR(50) NOT NULL
);

CREATE TABLE billing.payments (
                                  id BIGSERIAL PRIMARY KEY,
                                  billing_account_id BIGINT NOT NULL REFERENCES billing.billing_accounts(id),
                                  receipt_number VARCHAR(255) NOT NULL UNIQUE,
                                  payment_date DATE NOT NULL,
                                  amount DECIMAL(19, 2) NOT NULL,
                                  payment_method VARCHAR(100) NOT NULL,
                                  reference_number VARCHAR(255),
                                  collected_by VARCHAR(255)
);

CREATE TABLE billing.bank_transactions (
                                           id BIGSERIAL PRIMARY KEY,
                                           bank_date DATE NOT NULL,
                                           description TEXT,
                                           amount DECIMAL(19, 2) NOT NULL,
                                           status VARCHAR(50) NOT NULL,
                                           matched_payment_id BIGINT
);

-- =============================================================================
-- 4. CLAIMS SCHEMA
-- =============================================================================

CREATE TABLE claims.claims (
                               id BIGSERIAL PRIMARY KEY,
                               member_id BIGINT NOT NULL REFERENCES member.members(id),
                               claim_number VARCHAR(255) NOT NULL UNIQUE,
                               claim_date DATE NOT NULL,
                               deceased_name VARCHAR(255) NOT NULL,
                               relationship_to_member VARCHAR(100) NOT NULL,
                               date_of_death DATE NOT NULL,
                               claim_type VARCHAR(100) NOT NULL,
                               submission_channel VARCHAR(100),
                               notes TEXT,
                               payout_amount DECIMAL(19, 2),
                               status VARCHAR(50) NOT NULL,
                               approval_notes TEXT
);

CREATE TABLE claims.claim_status_history (
                                             id BIGSERIAL PRIMARY KEY,
                                             claim_id BIGINT NOT NULL REFERENCES claims.claims(id) ON DELETE CASCADE,
                                             status VARCHAR(50) NOT NULL,
                                             change_date TIMESTAMP NOT NULL,
                                             changed_by VARCHAR(255),
                                             remarks TEXT
);

-- =============================================================================
-- 5. PARTNER SCHEMA
-- =============================================================================

CREATE TABLE partner.partners (
                                  id BIGSERIAL PRIMARY KEY,
                                  name VARCHAR(255) NOT NULL,
                                  partner_category VARCHAR(100),
                                  contact_number VARCHAR(50),
                                  email VARCHAR(255),
                                  sla_adherence DOUBLE PRECISION,
                                  rating DOUBLE PRECISION,
                                  status VARCHAR(50)
);

-- =============================================================================
-- 6. TREASURY SCHEMA
-- =============================================================================

CREATE TABLE treasury.financial_periods (
                                            id BIGSERIAL PRIMARY KEY,
                                            period_name VARCHAR(255),
                                            status VARCHAR(50),
                                            closed_by VARCHAR(255),
                                            closed_date DATE
);

CREATE TABLE treasury.gl_accounts (
                                      id BIGSERIAL PRIMARY KEY,
                                      code VARCHAR(50) NOT NULL UNIQUE,
                                      name VARCHAR(255) NOT NULL,
                                      type VARCHAR(50) NOT NULL,
                                      balance DECIMAL(19, 2) DEFAULT 0.00,
                                      status VARCHAR(50) NOT NULL
);

CREATE TABLE treasury.journal_entries (
                                          id BIGSERIAL PRIMARY KEY,
                                          journal_id VARCHAR(255),
                                          entry_date DATE NOT NULL,
                                          debit_account VARCHAR(50),
                                          credit_account VARCHAR(50),
                                          amount DECIMAL(19, 2),
                                          reference VARCHAR(255),
                                          posted_by VARCHAR(255)
);

-- =============================================================================
-- 7. AUDIT SCHEMA
-- =============================================================================

CREATE TABLE audit.audit_logs (
                                  id BIGSERIAL PRIMARY KEY,
                                  timestamp TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
                                  username VARCHAR(255),
                                  action VARCHAR(100),
                                  entity_name VARCHAR(255),
                                  changes TEXT
);