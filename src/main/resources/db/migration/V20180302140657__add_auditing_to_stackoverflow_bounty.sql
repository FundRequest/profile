ALTER TABLE stack_overflow_bounty
  ADD creation_date TIMESTAMP,
  ADD last_modified_date TIMESTAMP,
  ADD created_by VARCHAR(100),
  ADD last_modified_by VARCHAR(100);