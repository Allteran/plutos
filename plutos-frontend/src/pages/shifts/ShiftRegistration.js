import {Button, Checkbox, DatePicker, Form, InputNumber, Select, Space, Spin, Typography} from "antd";
import {useEffect, useState} from "react";
import {COMPANY, STORAGE_KEY_TOKEN, URLS} from "../../util/const";
import {validateToken} from "../../util/authUtils";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import moment from "moment";

export default function ShiftRegistration() {
    const navigate = useNavigate();
    const [breakIn, setBreakIn] = useState(false);
    const [workedHours, setWorkedHours] = useState(0);
    const [workedMinutes, setWorkedMinutes] = useState(0);
    const [rate, setRate] = useState(0);
    const [income, setIncome] = useState(0);
    const [companyList, setCompanyList] = useState([]);
    const [selectedCompanyId, setSelectedCompanyId] = useState({});
    const [loadingPage, setLoadingPage] = useState(true);
    const [agency, setAgency] = useState({});

    const [form] = Form.useForm();

    async function getCompanyList() {
        let token = localStorage.getItem(STORAGE_KEY_TOKEN);
        await axios.get(URLS.COMPANIES + 'search/type', {
            headers: {
                'Authorization': 'Bearer ' + token,
            },
            params: {
                type: COMPANY.TYPE.WAREHOUSE
            }
        }).then(res => {
            res.data.map((item) => {
                item.label = item.name;
                item.value = item.id;
            });
            setCompanyList(res.data);
        }).catch(er => {
            console.log('getCompanyList error = ', er);
            validateToken(token).catch(er => {
                navigate("/login");
            });
        });
    }
    async function getProfile(token) {
        await axios.get(URLS.USERS.PROFILE, {
            headers: {
                'Authorization': 'Bearer ' + token,
            },
            params: {
                token: token
            }
        }).then(result => {
            let profile = result.data;
            let r = profile.ratePerHour;
            setRate(r);
            getAgency(profile.employerId, token);
        })
            .catch(er => {
                console.error(er);
            })
    }

    async function getAgency(id, token) {
        await axios.get(URLS.COMPANIES + "get/" + id, {
            headers: {
                'Authorization': 'Bearer ' + token,
            }
        }).then(result => {
            setAgency(result.data);
        })
    }

    useEffect( ()  => {
        let token = localStorage.getItem(STORAGE_KEY_TOKEN);
        getCompanyList();
        getProfile(token).then(res => {
            setLoadingPage(false);
        });
    }, []);

    const onBreakCheckChange = (e) => {
        setBreakIn(e.target.checked);
        if(!e.target.checked) {
            form.setFieldValue("breakDuration", 0);
        }
        calculateWorkedHours(form.getFieldValue("shiftStartDate"), form.getFieldValue("shiftStartTime"), form.getFieldValue("shiftEndDate"),
            form.getFieldValue("shiftEndTime"), form.getFieldValue("breakDuration"));
    };

    const onBreakDurationChange = (value) => {
        calculateWorkedHours(form.getFieldValue("shiftStartDate"), form.getFieldValue("shiftStartTime"), form.getFieldValue("shiftEndDate"),
            form.getFieldValue("shiftEndTime"), value);
    }

    const onCompanySelectChange = (value) => {
        setSelectedCompanyId(value);
    }
    const onRateChange = (value) => {
        if(value === null || value <= 0) {
            value = 0;
        }
        setRate(value);
        calculateIncome(value, workedHours, workedMinutes);
    }
    
    const onFinish = (values) => {
        calculateIncome(rate,  workedHours, workedMinutes);

    }

    const calculateWorkedHours = (startDate, startTime, endDate, endTime, breakDuration) => {
        let startTimeDate = new Date(startTime);
        let start = new Date(startDate);
        start.setHours(startTimeDate.getHours());
        start.setMinutes(startTimeDate.getMinutes());
        start.setSeconds(0);

        let endTimeDate = new Date(endTime);
        let end = new Date(endDate);
        end.setHours(endTimeDate.getHours());
        end.setMinutes(endTimeDate.getMinutes());
        end.setSeconds(0);

        let totalMinutes = Math.round((end.getTime() - start.getTime() - breakDuration * 60000) / 60000);
        console.log('totalMinutes = ', totalMinutes);
        let hours = Math.floor(totalMinutes / 60);
        let minutes = totalMinutes % 60;

        setWorkedHours(hours);
        setWorkedMinutes(minutes);

        calculateIncome(rate, workedHours, workedMinutes);

    }


    const calculateIncome = (rate, workedHours, workedMinutes) => {
        setIncome(rate * (workedHours + workedMinutes/60))
    }

    if(loadingPage) {
        return(
            <>
                <Spin tip="Завантажуємо інформацію" size="large" className="shift-registration-spin"/>
            </>
        )
    }

    return(
        <>
            <Typography.Title level={2}>Зміна: створення нового запису</Typography.Title>
            <Form
                form={form}
                initialValues={
                    {
                        ["breakDuration"]:0,
                        ["ratePerHour"]:rate
                    }
                }

                labelCol={{
                    span: 8,
                }}
                wrapperCol={{
                    span: 16,
                }}
                scrollToFirstError
                name="shift-registration"
                onFinish={onFinish}
                className="shift-registration">
                <Form.Item
                    label="Початок зміни" required={true}>
                    <Space>
                        <Form.Item name="shiftStartDate" noStyle rules={[
                            {
                                required: true,
                                message: 'Оберіть дату початку'
                            },
                            {
                                type: 'date',
                                message: 'Оберіть дату початку'
                            },
                            ({getFieldValue}) => ({
                                validator(_, value) {
                                    let endDateTime = new Date(getFieldValue('shiftEndDate') + getFieldValue('shiftEndTime'));
                                    let startDateTime = new Date(value + getFieldValue('shiftStartTime'));
                                    if(endDateTime.getTime() >= startDateTime.getTime()) {
                                        return Promise.resolve();
                                    }
                                    return Promise.reject()
                                }
                            })
                        ]}>
                            <DatePicker picker="date" placeholder="Оберіть дату"/>
                        </Form.Item>
                        <Form.Item name="shiftStartTime" noStyle rules={[
                            {
                                required: true,
                                message: 'Оберіть дату початку'
                            },
                            {
                                type: 'date',
                                message: 'Оберіть дату початку'
                            },
                            ({getFieldValue}) => ({
                                validator(_, value) {
                                    let endDateTime = new Date(getFieldValue('shiftEndDate') + getFieldValue('shiftEndTime'));
                                    let startDateTime = new Date(getFieldValue('shiftStartDate') + value);
                                    if(endDateTime.getTime() >= startDateTime.getTime()) {
                                        return Promise.resolve();
                                    }
                                    return Promise.reject()
                                }
                            })
                        ]}>
                            <DatePicker picker="time" style={{width: "70%%"}} placeholder="Оберіть час"/>
                        </Form.Item>
                    </Space>
                </Form.Item>
                <Form.Item label="Кінець зміни" required={true}>
                    <Space >
                        <Form.Item name="shiftEndDate" noStyle required={true} rules={[
                            {
                                required: true,
                                message: 'Оберіть дату початку'
                            },
                            {
                                type: 'date',
                                message: 'Оберіть дату початку'
                            },
                            ({getFieldValue}) => ({
                                validator(_, value) {
                                    let endDateTime = new Date(value + getFieldValue('shiftEndTime'));
                                    let startDateTime = new Date(getFieldValue('shiftStartDate') + getFieldValue('shiftStartTime'));
                                    if(endDateTime.getTime() >= startDateTime.getTime()) {
                                        return Promise.resolve();
                                    }
                                    return Promise.reject()
                                }
                            })
                        ]}>
                            <DatePicker picker="date" style={{width: "70%%"}} placeholder="Оберіть дату"/>
                        </Form.Item>
                        <Form.Item name="shiftEndTime" noStyle required={true} rules={[
                            {
                                required: true,
                                message: 'Оберіть дату початку'
                            },
                            {
                                type: 'date',
                                message: 'Оберіть дату початку'
                            },
                            ({getFieldValue}) => ({
                                validator(_, value) {
                                    let endDateTime = new Date(getFieldValue('shiftEndDate') + value);
                                    let startDateTime = new Date(getFieldValue('shiftStartDate') + getFieldValue('shiftStartTime'));
                                    if(endDateTime.getTime() >= startDateTime.getTime()) {
                                        calculateWorkedHours(getFieldValue('shiftStartDate'), getFieldValue('shiftStartTime'), getFieldValue('shiftEndDate'),
                                            value, getFieldValue('breakDuration'));
                                        return Promise.resolve();
                                    }
                                    return Promise.reject(new Error('Час та дата початку повинні бути до часу та дати кінця'));
                                }
                            })
                        ]}>
                            <DatePicker picker="time" style={{width: "70%%"}} placeholder="Оберіть час"/>
                        </Form.Item>
                    </Space>
                </Form.Item>
                <Form.Item label="Перерва" required={true}>
                    <Space>
                        <Form.Item noStyle>
                            <Checkbox onChange={onBreakCheckChange} style={{width: "50%%"}} checked={breakIn}>Вираховується</Checkbox>
                        </Form.Item>
                        <Form.Item name="breakDuration" noStyle rules={[
                            {
                                required: breakIn,
                                message: 'Вкажіть час перерви (в хв)'
                            },
                            {
                                type: 'number',
                                message: 'Вкажіть час перерви (в хв)'
                            },
                            ({getFieldValue}) => ({
                                validator(_, value) {
                                    if(breakIn) {
                                        if(value <= 0) {
                                            calculateWorkedHours(getFieldValue('shiftStartDate'), getFieldValue('shiftStartTime'),
                                                getFieldValue('shiftEndDate'), getFieldValue('shiftEndTime'), value);
                                            return Promise.reject(new Error('Вкажіть корректний час перерви'));
                                        }
                                    }
                                    return Promise.resolve();

                                }
                            })
                        ]}>
                            <InputNumber addonAfter="хв" style={{
                                width: 'calc(75% - 9px)'
                            }} disabled={!breakIn}
                            onChange={onBreakDurationChange}/>
                        </Form.Item>
                    </Space>
                </Form.Item>
                <Form.Item name="workedHours" label="Пропрацьовано часу">
                    <span className="ant-form-text">{workedHours} годин {workedMinutes} хвилин</span>
                </Form.Item>
                <Form.Item name="ratePerHour" label="Ставка" required={true}>
                    <InputNumber addonAfter="зл/год." style={{
                        display: 'inline-block',
                        width: '78%'
                    }} inputMode="decimal" onChange={onRateChange}/>
                </Form.Item>
                <Form.Item label="Дохід">
                    <span className="ant-form-text">{income} зл.</span>
                </Form.Item>
                <Form.Item name="company" label="Компанія">
                    <Select onChange={onCompanySelectChange} options={companyList} placeholder="Виберіть зі списку"/>
                </Form.Item>
                <Form.Item label="Агенція">
                    <span className="ant-form-text">{agency.name}</span>
                </Form.Item>
                <Form.Item label="  " colon={false}>
                    <Button type="primary" htmlType="submit">
                        Створити запис
                    </Button>
                </Form.Item>
            </Form>


        </>
    )
}
