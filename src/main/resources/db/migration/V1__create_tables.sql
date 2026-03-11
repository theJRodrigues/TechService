CREATE TABLE companies(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(150) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE accounts(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    company_id UUID NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_account_company
        FOREIGN KEY (company_id)
        REFERENCES companies(id),

    CONSTRAINT chk_account_role
        CHECK (role IN ('ADMIN', 'TECHNICIAN', 'ATTENDANT'))
);

CREATE TABLE clients(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(150) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL,
    document_type VARCHAR(10) NOT NULL,
    document VARCHAR(14) NOT NULL,

    zip_code VARCHAR(8) NOT NULL,
    street VARCHAR(255) NOT NULL,
    number varchar(20) NOT NULL,
    neighborhood VARCHAR(80),
    city VARCHAR(80),
    complement VARCHAR(100),

    company_id UUID NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_client_company
        FOREIGN KEY (company_id)
        REFERENCES companies(id),

    CONSTRAINT chk_client_document_type
        CHECK(document_type IN('CNPJ', 'CPF'))
);

CREATE TABLE service_orders(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    client_id UUID NOT NULL,
    company_id UUID NOT NULL,
    technician_id UUID,
    created_by UUID NOT NULL,

    equipment_brand VARCHAR(100),
    equipment_model VARCHAR(100),
    equipment_serial_number VARCHAR(100),

    reported_issue TEXT NOT NULL,
    technical_diagnosis TEXT,
    service_performed TEXT,
    notes TEXT,

    opened_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    started_at TIMESTAMP,
    finished_at TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN',

    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_service_order_client
        FOREIGN KEY (client_id)
        REFERENCES clients(id),

    CONSTRAINT fk_service_order_company
        FOREIGN KEY (company_id)
        REFERENCES companies(id),

    CONSTRAINT fk_service_order_technician
        FOREIGN KEY (technician_id)
        REFERENCES  accounts(id),

    CONSTRAINT fk_service_order_created_by
        FOREIGN KEY (created_by)
        REFERENCES accounts(id),

    CONSTRAINT chk_service_order_status
        CHECK(status IN ('OPEN','WAITING_APPROVAL', 'IN_PROGRESS', 'CANCELED', 'FINISHED'))

);

CREATE TABLE refresh_tokens(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID NOT NULL,
    company_id UUID NOT NULL,
    role VARCHAR(20) NOT NULL,
    token_hash VARCHAR(255) NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT false,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_refresh_token_account
        FOREIGN KEY (account_id)
        REFERENCES accounts(id),

    CONSTRAINT fk_refresh_token_company
        FOREIGN KEY (company_id)
        REFERENCES companies(id),

    CONSTRAINT chk_refresh_token_role
        CHECK (role IN ('ADMIN', 'TECHNICIAN', 'ATTENDANT'))
);