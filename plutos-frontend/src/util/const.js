    export const HEADER_AUTH='Authorization';
    export const STORAGE_KEY_TOKEN = 'STORAGE_KEY_TOKEN';
    export const STORAGE_KEY_LOGIN = 'STORAGE_KEY_USERNAME';
    export const STORAGE_KEY_USER_ID = 'storage_key_uSeR_iD';
    export const URL_LOGIN = 'http://localhost:8080/auth/login'
    export const URL_VALIDATE_TOKEN = 'http://localhost:8080/auth/validateToken';
    export const URL_SHIFT_LIST_PUBLIC = 'http://localhost:8080/route/shifts/search/user';
    export const URL_DOMAIN = 'http://localhost:8080/';
    export const URLS = {
        COMPANIES: URL_DOMAIN + 'route/companies/',
        USERS: {
            USERS: URL_DOMAIN + 'route/users/',
            PROFILE: URL_DOMAIN + 'route/users/profile',
        }
    }

    export const COMPANY = {
        TYPE: {
            AGENCY: 'AGENCY',
            WAREHOUSE: 'WAREHOUSE'
        }
    }