BEGIN TRANSACTION;
CREATE TABLE "word" (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`categoryID`	INTEGER NOT NULL,
	`arabic`	TEXT NOT NULL,
	`english`	TEXT NOT NULL,
	`arabicPronounce`	TEXT,
	`englishPronounce`	TEXT,
	FOREIGN KEY(`categoryID`) REFERENCES category
);
INSERT INTO `word` VALUES (1,1,'قطّة','cat','كات','qetah');
INSERT INTO `word` VALUES (2,1,'كلب','dog','دوق','kalb');
INSERT INTO `word` VALUES (3,1,'بقرة','cow','كاو','baqara');
INSERT INTO `word` VALUES (4,1,'حصان','horse','هورس','hesan');
INSERT INTO `word` VALUES (5,1,'جمل','camel','كاميل','jaml');
INSERT INTO `word` VALUES (6,1,'أرنب','rabbit','رابيت','arnab');
INSERT INTO `word` VALUES (7,1,'فأر','mice','مايس','faa''r');
INSERT INTO `word` VALUES (8,1,'بطة','duck','دك','battah');
INSERT INTO `word` VALUES (9,1,'ثعلب','fox','فوكس','thaa''lb');
INSERT INTO `word` VALUES (10,1,'ضفدع
','frog','فروق','thifdaa''');
INSERT INTO `word` VALUES (11,2,'تفّاح','apple','أبل','tuffah');
INSERT INTO `word` VALUES (12,2,'برتقال','orange','أورانج','burtuqaal');
INSERT INTO `word` VALUES (13,2,'موز','banana','بانانا','mooz');
INSERT INTO `word` VALUES (14,2,'ليمون','lemon','ليمون','laymuun');
INSERT INTO `word` VALUES (15,2,'توت','berry','بيري','toot');
INSERT INTO `word` VALUES (16,2,'جزر','carrot','كاروت','jazar');
INSERT INTO `word` VALUES (17,2,'خيار','cucumber','كيوكمبر','khiyaar');
INSERT INTO `word` VALUES (18,2,'طماطم','tomato','توماتو','tamatm');
INSERT INTO `word` VALUES (19,2,'ذرة','corn','كورن','theura');
INSERT INTO `word` VALUES (20,2,'بيض','egg','اييق','baieth');
CREATE TABLE `category` (
	`categoryID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`category`	TEXT NOT NULL
);
INSERT INTO `category` VALUES (1,'animal');
INSERT INTO `category` VALUES (2,'food');
COMMIT;
