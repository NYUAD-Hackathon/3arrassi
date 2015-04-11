BEGIN TRANSACTION;
CREATE TABLE `words` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`category`	TEXT NOT NULL,
	`arabic`	TEXT NOT NULL,
	`english`	TEXT NOT NULL
);
INSERT INTO `words` VALUES (1,'animal','قطّة','cat');
INSERT INTO `words` VALUES (2,'animal','كلب','dog');
INSERT INTO `words` VALUES (3,'animal','بقرة','cow');
INSERT INTO `words` VALUES (4,'animal','حصان','horse');
INSERT INTO `words` VALUES (5,'animal','جمل','camel');
INSERT INTO `words` VALUES (6,'animal','أرنب','rabbit');
INSERT INTO `words` VALUES (7,'animal','فأر','mice');
INSERT INTO `words` VALUES (8,'animal','بطة','duck');
INSERT INTO `words` VALUES (9,'animal','ثعلب','fox');
INSERT INTO `words` VALUES (10,'animal','ضفدع
','frog');
INSERT INTO `words` VALUES (11,'country','أفغانستان','Afganistan');
INSERT INTO `words` VALUES (12,'country','البرازيل','Brazil');
INSERT INTO `words` VALUES (13,'country','كندا','Canada');
INSERT INTO `words` VALUES (14,'country','مصر','Egypt');
INSERT INTO `words` VALUES (15,'country','فرنسا','France');
INSERT INTO `words` VALUES (16,'country','ألمانيا','Germany');
INSERT INTO `words` VALUES (17,'country','الهند','India');
INSERT INTO `words` VALUES (18,'country','العراق','Iraq');
INSERT INTO `words` VALUES (19,'country','كوريا','Korea');
INSERT INTO `words` VALUES (20,'country','ماليزيا','Malasya');
INSERT INTO `words` VALUES (21,'Food','حساء','soup');
INSERT INTO `words` VALUES (22,'Food','خبز','bread');
INSERT INTO `words` VALUES (23,'Food','فراولة','strawberry');
INSERT INTO `words` VALUES (24,'Food','سوشي','sushi');
INSERT INTO `words` VALUES (25,'Food','جبن','cheese');
INSERT INTO `words` VALUES (26,'Food','ليمون','lemon');
INSERT INTO `words` VALUES (27,'Food','الأرز','rice');
INSERT INTO `words` VALUES (28,'Food','البطاطس','potato');
INSERT INTO `words` VALUES (29,'Food','الكسكس','Couscous');
INSERT INTO `words` VALUES (30,'Food','سمك','fish');
INSERT INTO `words` VALUES (31,'Clothes','Dress ','Dress ');
INSERT INTO `words` VALUES (32,'Clothes','قميص','Shirt');
INSERT INTO `words` VALUES (33,'Clothes','
قبعة
','Cap');
INSERT INTO `words` VALUES (34,'Clothes','معطف','Coat');
INSERT INTO `words` VALUES (35,'Clothes','حذاء','Shoes');
INSERT INTO `words` VALUES (36,'Clothes','نظارات','Glasses');
INSERT INTO `words` VALUES (37,'Clothes','سروال قصير','Shorts');
INSERT INTO `words` VALUES (38,'Clothes','منامة
','Pyjamas');
INSERT INTO `words` VALUES (39,'Clothes','سترة
','Jacket');
INSERT INTO `words` VALUES (40,'Clothes','سروال جينز','jeans');
COMMIT;
