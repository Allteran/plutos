import axios from "axios";
import { URL_VALIDATE_TOKEN, STORAGE_KEY_TOKEN } from "./const";

export async function validateToken(token){
    await axios.post(URL_VALIDATE_TOKEN, null, {
        params: {
            token: token
        }
    }).catch(er => {
        throw new Error('Token error');
    })
}



