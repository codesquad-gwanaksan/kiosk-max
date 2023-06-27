drop all objects;

create TABLE `category`
(
    `category_id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `category_name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`category_id`)
);

create TABLE `product`
(
    `product_id`        BIGINT       NOT NULL AUTO_INCREMENT,
    `category_id`       BIGINT       NOT NULL,
    `product_name`      VARCHAR(50)  NOT NULL,
    `product_price`     BIGINT       NOT NULL,
    `product_image`     VARCHAR(200) NOT NULL,
    `product_is_best`   BOOL NULL,
    `product_has_hot`   BOOL NULL,
    `product_has_ice`   BOOL NULL,
    `product_has_large` BOOL NULL,
    `product_has_small` BOOL NULL,
    PRIMARY KEY (`product_id`, `category_id`),
    FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
);

create TABLE `orders`
(
    `order_id`              BIGINT      NOT NULL AUTO_INCREMENT,
    `order_time`            DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`order_id`)
);

create TABLE `payment`
(
    `payment_id`     BIGINT       NOT NULL AUTO_INCREMENT,
    `order_id`       BIGINT       NOT NULL,
    `payment_total_amount`    BIGINT      NOT NULL,
    `payment_received_amount` BIGINT      NOT NULL,
    `payment_change`          BIGINT NULL,
    `payment_method` VARCHAR(10)  NOT NULL,
    PRIMARY KEY (`payment_id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
);

create TABLE `order_product`
(
    `order_product_id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `order_id`                  BIGINT      NOT NULL,
    `product_id`                BIGINT      NOT NULL,
    `order_product_cnt`         BIGINT      NOT NULL,
    `order_product_size`        VARCHAR(50) NOT NULL,
    `order_product_temperature` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`order_product_id`, `order_id`, `product_id`),
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
    FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
);

create TABLE `order_log`
(
    `product_id` BIGINT   NOT NULL,
    `sales_cnt`  BIGINT   NOT NULL,
    `sales_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
