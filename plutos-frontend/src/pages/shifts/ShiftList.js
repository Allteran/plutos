import {Button, Modal, Space, Table} from "antd";
import axios from "axios";
import {STORAGE_KEY_TOKEN, STORAGE_KEY_USER_ID, URL_SHIFT_LIST_PUBLIC} from "../../util/const";
import {validateToken} from "../../util/authUtils";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import moment from "moment";
import { Typography } from "antd";

export default function ShiftList() {
    const navigate = useNavigate();
    const [shiftList, setShiftList] = useState();
    const [loading, setLoading] = useState(true);
    const [modalLoading, setModalLoading] = useState(false);
    const [openModal, setOpenModal] = useState(false);
    const [modalText, setModalText] = useState('Увага! Відмініти цю дію неможливо. Ви впевнені, що хочете видалити запис?');
    const [deleteRecordId, setDeleteRecordId] = useState('');
    const size = 'middle';
    const currentDate = new Date();
    const titleDateFrom = moment(new Date(currentDate.getFullYear(), currentDate.getMonth(), 1)).format('DD.MM.YYYY')
    const titleDateTo = moment(currentDate).format('DD.MM.YYYY');


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
        console.log('Deleting shift record: ', deleteRecordId);
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
            title: 'Перерва',
            dataIndex: 'breakDuration',
            key: 'breakDuration',
            render: (_, record) => (
                    <div>{record.breakDuration} хв</div>
    )
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
            title: 'Дії',
            key: 'actions',
            render: (_, record) => (
                <Space size="middle">
                    <Button className="table-btn" size="small" type="link" onClick={() => navToEditPage(record)}>Редагувати</Button>
                    <Button className="table-btn" size="small" type="link" onClick={() => showModalDeleteSalary(record)}>Видалити</Button>
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

    function navToEditPage(record) {
        console.log('Edit page for record with ID:' + record.id);
        return null;
    }

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
            res.data.map(el => {
                el.shiftStart = dateTimeFormat(el.shiftStart);
                el.shiftEnd = dateTimeFormat(el.shiftEnd);
                return el;
            });
            setShiftList(res.data.map(el =>({...el, key:el.id})));
            setLoading(false)
        }).catch(er => {
            validateToken(token).catch(er => {
                navigate("/login");
            });
        })
    }

    useEffect( ()  => {
        getSalaryList();
    }, []);

    function dateTimeFormat(dateTime) {
        return moment(dateTime).format('DD.MM.YYYY HH:mm');
    }

    return (
        <div id="shiftList">
            <Typography.Title className="title-main" level={4}>Дані по змінам за період з {titleDateFrom} по {titleDateTo}</Typography.Title>
            <Table
                {...tableProps}
                dataSource={shiftList}
                bordered={true}
                columns={columns}/>
        </div>

    );
}