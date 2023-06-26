insert into category (category_name) values ('COFFEE');
insert into category (category_name) values ('LATTE');
insert into category (category_name) values ('TEA');
insert into category (category_name) values ('JUICE');
insert into category (category_name) values ('SPARKLING');

insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (1, '아메리카노', 4900, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/COFFE/Americano.jpg', 1, 0, 1, 0, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (1, '카라멜 마키아또', 5100, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/COFFE/CaramelMacchiato.jpg', 0, 1, 1, 1, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (1, '롱블랙', 5300, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/COFFE/LongBack.jpg', 0, 0, 1, 0, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (1, '프렌치 아메리카노', 5500, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/COFFE/FrenchAmericano.jpg', 0, 0, 1, 0, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (1, '콜드브루', 5700, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/COFFE/ColdBrew.jpg', 0, 0, 1, 1, 0);

insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (2, '카페 라떼', 4500, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/LATTE/CafeLatte.jpg', 1, 0, 1, 0, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (2, '초콜릿 라떼', 5000, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/LATTE/ChocolateLatte.jpg', 0, 0, 1, 1, 0);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (2, '더 그린 쑥 크림 라떼', 5000, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/LATTE/TheGreenMugwortCreamLatte.jpg', 0, 1, 1, 1, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (2, '라벤더 카페 브레베', 5000, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/LATTE/LavenderCafeBrevet.png', 0, 1, 0, 1, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (2, '바닐라 빈 라떼', 5000, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/LATTE/VanillaBeanLatte.jpg', 0, 1, 1, 1, 1);

insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (3, '민트 블렌드 티', 3500, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/TEA/MintBlendTea.jpg', 1, 1, 1, 1, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (3, '블루밍 프루트 유스베리 티', 3500, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/TEA/BloomingFruitYouthberryTea.jpg', 0, 1, 1, 1, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (3, '아이스 민트 블렌드 티', 3500, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/TEA/IceMintBlendTea.jpg', 0, 0, 1, 1, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (3, '아이스 얼 그레이 티', 3500, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/TEA/IceEarlGrayTea.jpg', 0, 0, 1, 1, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (3, '아이스 유스베리 티', 3500, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/TEA/IceYouthVerity.jpg', 0, 0, 1, 1, 1);

insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (4, '딸기 주스', 5500, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/JUICE/StrawberryJuice.jpg', 1, 0, 1, 0, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (4, '필 더 그린', 5500, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/JUICE/FeelTheGreen.jpg', 0, 0, 1, 0, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (4, '필 더 레드', 5500, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/JUICE/FeelTheRed.jpg', 0, 0, 1, 0, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (4, '필 더 옐로우', 5500, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/JUICE/FeelTheYellow.jpg', 0, 0, 1, 0, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (4, '망고 주스', 5500, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/JUICE/MangoJuice.jpg', 0, 0, 1, 0, 1);

insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (5, '사과 스파클링', 3900, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/SPARKLING/AppleSparkling.jpg', 1, 0, 1, 1, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (5, '오렌지 스파클링', 3900, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/SPARKLING/OrangeSparkling.jpg', 0, 0, 1, 1, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (5, '선셋 스파클링', 3900, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/SPARKLING/SunsetSparkling.jpg', 0, 0, 1, 1, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (5, '피치 스파클링', 3900, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/SPARKLING/PeachSparkling.jpg', 0, 0, 1, 1, 1);
insert into product (category_id, product_name, product_price, product_image, product_is_best, product_has_hot,
                     product_has_ice, product_has_large, product_has_small)
values (5, '레몬 스파클링', 3900, 'https://codesquad-project.s3.ap-northeast-2.amazonaws.com/public/img/SPARKLING/LemonSparkling.jpg', 0, 0, 1, 1, 1);
