CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE UNIQUE INDEX idx_companies_cnpj ON companies(cnpj);
CREATE INDEX idx_companies_is_active on companies(is_active);

CREATE UNIQUE INDEX idx_accounts_email ON accounts(email);
CREATE INDEX idx_accounts_company_id ON accounts(company_id);
CREATE INDEX idx_accounts_company_active ON accounts(company_id, is_active);
CREATE INDEX idx_accounts_company_role ON accounts(company_id, role);

CREATE INDEX idx_clients_company_id ON clients(company_id);
CREATE INDEX idx_clients_company_active ON clients(company_id, is_active);
CREATE INDEX idx_clients_name_trgm ON clients USING gin(name gin_trgm_ops);
CREATE INDEX idx_clients_document ON clients(document);

CREATE INDEX idx_service_orders_client_id ON service_orders(client_id);
CREATE INDEX idx_service_orders_company_id ON service_orders(company_id);
CREATE INDEX idx_service_orders_technician_id ON service_orders(technician_id);
CREATE INDEX idx_service_orders_created_by ON service_orders(created_by);
CREATE INDEX idx_service_orders_status ON service_orders(status);
CREATE INDEX idx_service_orders_company_status ON service_orders(company_id, status);
CREATE INDEX idx_service_orders_created_at ON service_orders(created_at DESC);
CREATE INDEX idx_service_orders_company_active_status
ON service_orders(company_id, is_active, status);

CREATE UNIQUE INDEX idx_refresh_tokens_token_hash ON refresh_tokens(token_hash);
CREATE INDEX idx_refresh_tokens_account_id ON refresh_tokens(account_id);
CREATE INDEX idx_refresh_tokens_revoked ON refresh_tokens(revoked);
CREATE INDEX idx_refresh_tokens_expires_at ON refresh_tokens(expires_at);



