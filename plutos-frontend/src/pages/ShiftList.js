import {Table} from "antd";
import axios from "axios";
import {STORAGE_KEY_TOKEN, STORAGE_KEY_USER_ID, URL_SHIFT_LIST_PUBLIC} from "../util/const";
import {useEffect, useState} from "react";

export default function ShiftList() {
    const [shiftList, setShiftList] = useState();
    const [loading, setLoading] = useState(true);
    const size = 'middle';


    const tableProps = {
        loading,
        size
    }

    const columns = [
        {
            title: 'Початок зміни',
            dataIndex: 'shiftStart',
            key: 'shiftStart'
        },
        {
            title: 'Кінець зміни',
            dataIndex: 'shiftEnd',
            key: 'shiftEnd'
        },
        {
            title: 'Кількість годин',
            dataIndex: 'workedHours',
            key: 'workedHours',
        },
        {
            title: 'Ставка',
            dataIndex: 'ratePerHour',
            key: 'ratePerHour',
        },
        {
            title: 'Прибуток',
            dataIndex: 'income',
            key: 'income',
        },
        {
            title: 'Результативність',
            dataIndex: 'efficiency',
            key: 'efficiency',
        }
    ];

    const getShifts = async () => {
        let token = localStorage.getItem(STORAGE_KEY_TOKEN);
        let userId = localStorage.getItem(STORAGE_KEY_USER_ID);
        await axios.get(URL_SHIFT_LIST_PUBLIC + userId, {
            headers: {
                'Authorization': 'Bearer ' + token,
            }
        }).then(res => {
            setShiftList(res.data.map(el =>({...el, key: el.id})));
            setLoading(false);
        })
    };

    useEffect(() => {
        getShifts().then(() => {
        });
    }, []);



    return (
        <div id="shiftList">
            <Table
                {...tableProps}
                dataSource={shiftList}
                columns={columns}/>
        </div>

    );
}