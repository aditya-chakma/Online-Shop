package net.therap.util;

/**
 * @author al.imran
 * @since 03/06/2021
 */
public interface StringConst {

    String PERSISTENCE_UNIT_NAME = "therap_shop";

    String SESSION_KEY_USER_ID = "id";
    String SESSION_KEY_USER_ROLE = "role";
    String SESSION_KEY_LOGGEDIN = "isLoggedin";
    String SESSION_KEY_CATEGORY_LIST = "categoryList";

    String IS_ADMIN = "isAdmin";
    String IS_CUSTOMER = "isCustomer";

    String REGEX_MOBILE_NUMBER = "\\+880[\\d]{10}+";

    String DATA_ROOT = "/opt/therap-shop/";
    String DEFAULT_IMAGE = "/opt/therap-shop/default.png";

    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
