DELETE FROM telegram_verifications
WHERE telegram_name LIKE 'QuintenDes';

ALTER TABLE telegram_verifications
  ADD COLUMN user_id VARCHAR(100);

INSERT INTO telegram_verifications (
  telegram_name, secret, user_id
) VALUES ('QuintenDes', 'QDS_FTW', '98fff982-8ead-40f9-a0c3-cc6f0feba928');