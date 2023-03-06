import {Button, Modal, Space, Table} from "antd";
import axios from "axios";
import {STORAGE_KEY_TOKEN, STORAGE_KEY_USER_ID, URL_SHIFT_LIST_PUBLIC} from "../util/const";
import {validateToken} from "../util/authUtils";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

export default function ShiftList() {
    const navigate = useNavigate();
    const [salaryList, setSalaryList] = useState();
    const [loading, setLoading] = useState(true);
    const [modalLoading, setModalLoading] = useState(false);
    const [openModal, setOpenModal] = useState(false);
    const [modalText, setModalText] = useState('Увага! Відмініти цю дію неможливо. Ви впевнені, що хочете видалити запис?');
    const [deleteRecordId, setDeleteRecordId] = useState('');
    const size = 'middle';

    const tableProps = {
        loading,
        size
    }
    const showModalDeleteSalary = (record) => {
        setDeleteRecordId(record.id)
        setOpenModal(true);

    };

    const confirmDelete = () => {
        setModalText('Видаляємо запис');
        console.log('Deleting salary record: ', deleteRecordId);
        setModalLoading(true);
        setOpenModal(false);
        setModalLoading(false)
        setModalText('Увага! Відмініти цю дію неможливо. Ви впевнені, що хочете видалити запис?')
    };

    const closeModal = () => {
        setOpenModal(false);
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
        },
        {
            title: 'Дії',
            key: 'actions',
            render: (_, record) => (
                <Space size="middle">
                    <Button type="link" onClick={() => showModalDeleteSalary(record)}>Видалити</Button>
                    <Modal
                        title="Видалення запису"
                        open={openModal}
                        onOk={confirmDelete}
                        okText="Видалити"
                        cancelText="Скасувати"
                        confirmLoading={modalLoading}
                        onCancel={closeModal}>
                        <p>{modalText}</p>
                    </Modal>



                </Space>
            )
        }
    ];

    // const deleteSalary = async (salaryId) => {
    //
    // };

    async function getSalaryList  () {
        let token = localStorage.getItem(STORAGE_KEY_TOKEN);
        let userId = localStorage.getItem(STORAGE_KEY_USER_ID);
        await axios.get(URL_SHIFT_LIST_PUBLIC, {
            headers: {
                'Authorization': 'Bearer ' + token,
            },
            params: {
                userId: userId
            }
        }).then(res => {
            setSalaryList(res.data.map(el =>({...el, key:el.id})));
            setLoading(false)
        }).catch(er => {
            console.log('getSalaryList: error = ', er);
            validateToken(token).catch(er => {
                navigate("/login");
            });
        })
    }

    useEffect( ()  => {
        getSalaryList()
    }, []);



    return (
        <div id="shiftList">
            <Table
                {...tableProps}
                dataSource={salaryList}
                columns={columns}/>
        </div>

    );
}