import {useRouteError} from "react-router-dom";

export default function ErrorPage() {
    const error = useRouteError();
    console.error(error);

    return (
        <div>
            <div id="error-page">
                <h1>Ой-ой!</h1>
                <p>Щось пішло не так! Ми приносимо вибачення за цю помилку, ми вже над нею працюємо.</p>
                <p>
                    <i>{error.statusText || error.statusMessage}</i>
                </p>
            </div>
        </div>
    );
}