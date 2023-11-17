
--- юзеры

INSERT INTO article_service.users(name)
VALUES ('Андрей');

INSERT INTO article_service.users(name)
VALUES ('Соня');

INSERT INTO article_service.users(name)
VALUES ('Томас');

--- статьи

INSERT INTO article_service.articles(author_id, creation_date_time, head, body)
VALUES (1, now(), 'Как праздновать ДР?',
 'In computing, plain text is a loose term for data (e.g. file contents) that represent only characters of readable material but not its graphical representation nor other objects (floating-point numbers, images, etc.). It may also include a limited number of "whitespace" characters that affect simple arrangement of text, such as spaces, line breaks, or tabulation characters. Plain text is different from formatted text, where style information is included; from structured text, where structural parts of the document such as paragraphs, sections, and the like are identified; and from binary files in which some portions must be interpreted as binary objects (encoded integers, real numbers, images, etc.). The term is sometimes used quite loosely, to mean files that contain only "readable" content (or just files with nothing that the speaker does not prefer). For example, that could exclude any indication of fonts or layout (such as markup, markdown, or even tabs);')


INSERT INTO article_service.articles(author_id, creation_date_time, head, body)
VALUES (2, now(), 'Что такое пирсинг?',
 'ОО.. в освременоонй жизни человека большую роль играет пирсинг...')

 INSERT INTO article_service.articles(author_id, creation_date_time, head, body)
 VALUES (3, now(), 'Недооценённые блюда из растений',
  'Сегодня я расскажу почему полезно есть чужие цветы. ИТАК...')
