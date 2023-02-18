import {Button, Table} from "antd";
import axios from "axios";
import {useEffect} from "react";

export default function ShiftList() {
    const URL_SHIFT_LIST = "http://localhost:8080/router/salary/search/user?userId=63b2dbaeebe1a13a221c028b"
    const TOKEN = 'eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl0sInN1YiI6InZpdGFsaWkucHJvemFwYXNAZ21haWwuY29tIiwiaWF0IjoxNjc2NzI1NDQ5LCJleHAiOjE2NzY4MTE4NDl9.l46eDUSzhcW_kfegLs2C2zGhIyybvQ0-wnVwLMzQ80NqUciaDFFDfxmYzw4hZIaMXJASQ-24bgMIfDZibZvTv';

    const columns = [
        {
            title: 'Дата',
            dataIndex: 'date',
            key: 'date',
        },
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

    const datasource = [
        {
            date: '01.01.2023',
            shiftStart: '07:00',
            shiftEnd: '19:00',
            workedHours: "12",
            ratePerHour: '16.60 zl',
            income: '2000 zl',
            efficiency: "120%"

        }
    ];

    const state = {
        shifts: []
    };

    const getShifts = async () => {
        console.log('Getting shift list from: ', URL_SHIFT_LIST);
        console.log('Header: Authorization: Bearer ' + TOKEN);
        await axios.get(URL_SHIFT_LIST, {
            headers: {
                'Authorization': 'Bearer ' + TOKEN
            }
        }).then(res => {
            this.setState({shifts: res.data})
        })
    };

    // useEffect(() => {
    //     getShifts().then();
    // }, []);



    return (
        <div id="shiftList">
            <Table dataSource={state.shifts} columns={columns}/>
            <Button type="primary" onClick={getShifts}>TEST DOWNLOAD SHIFTS</Button>
        </div>

    );
}