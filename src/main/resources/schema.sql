-- Digital Procurement Platform Database Schema

-- Opportunities Table
CREATE TABLE IF NOT EXISTS opportunities (
    id VARCHAR(255) PRIMARY KEY,
    version BIGINT,
    title VARCHAR(500) NOT NULL,
    description TEXT,
    buyer_id VARCHAR(255) NOT NULL,
    product_category VARCHAR(255) NOT NULL,
    region VARCHAR(255),
    min_budget DECIMAL(19,2),
    max_budget DECIMAL(19,2),
    currency VARCHAR(10),
    submission_deadline TIMESTAMP NOT NULL,
    delivery_start_date TIMESTAMP,
    delivery_end_date TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    published_at TIMESTAMP
);

-- Suppliers Table
CREATE TABLE IF NOT EXISTS suppliers (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(50),
    region VARCHAR(255),
    address TEXT,
    qualification_status VARCHAR(50) NOT NULL,
    registered_at TIMESTAMP NOT NULL,
    certification_expiry_date TIMESTAMP
);

-- Bids Table
CREATE TABLE IF NOT EXISTS bids (
    id VARCHAR(255) PRIMARY KEY,
    opportunity_id VARCHAR(255) NOT NULL,
    supplier_id VARCHAR(255) NOT NULL,
    bid_amount DECIMAL(19,2) NOT NULL,
    currency VARCHAR(10),
    proposal_details TEXT,
    status VARCHAR(50) NOT NULL,
    transaction_id VARCHAR(255) UNIQUE NOT NULL,
    submitted_at TIMESTAMP NOT NULL,
    FOREIGN KEY (opportunity_id) REFERENCES opportunities(id)
);

-- Contracts Table
CREATE TABLE IF NOT EXISTS contracts (
    id VARCHAR(255) PRIMARY KEY,
    opportunity_id VARCHAR(255),
    bid_id VARCHAR(255),
    buyer_id VARCHAR(255) NOT NULL,
    supplier_id VARCHAR(255) NOT NULL,
    contract_amount DECIMAL(19,2) NOT NULL,
    currency VARCHAR(10),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    terms TEXT
);

-- Indexes for better query performance
CREATE INDEX idx_opportunities_buyer ON opportunities(buyer_id);
CREATE INDEX idx_opportunities_category ON opportunities(product_category);
CREATE INDEX idx_opportunities_region ON opportunities(region);
CREATE INDEX idx_opportunities_status ON opportunities(status);

CREATE INDEX idx_bids_supplier ON bids(supplier_id);
CREATE INDEX idx_bids_opportunity ON bids(opportunity_id);

CREATE INDEX idx_contracts_buyer ON contracts(buyer_id);
CREATE INDEX idx_contracts_supplier ON contracts(supplier_id);
