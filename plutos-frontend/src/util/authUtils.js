import axios from "axios";
import {STORAGE_KEY_LOGIN, STORAGE_KEY_TOKEN, STORAGE_KEY_USER_ID, URL_VALIDATE_TOKEN} from "./const";

export async function validateToken(token){
    await axios.post(URL_VALIDATE_TOKEN, null, {
        params: {
            token: token
        }
    }).catch(er => {
        localStorage.setItem(STORAGE_KEY_TOKEN, null);
        localStorage.setItem(STORAGE_KEY_USER_ID, null);
        localStorage.setItem(STORAGE_KEY_LOGIN, null);
        throw new Error('Token error' + er);
    })
}

