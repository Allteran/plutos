import {Button, Checkbox, DatePicker, Form, InputNumber, Select, Space, Typography} from "antd";
import {useState} from "react";

export default function ShiftRegistration() {
    const [breakIn, setBreakIn] = useState(false);
    const [workedHours, setWorkedHours] = useState(0);
    const [workedMinutes, setWorkedMinutes] = useState(0);
    const [rate, setRate] = useState(0);
    const [income, setIncome] = useState(0);
    const companyList = [
        {
            value: 'HOPI_ID',
            label: 'HOOOPI',
        },
        {
            value: 'ORBICO_ID',
            label: 'ORBICO'
        }
    ]
    const onBreakCheckChange = (e) => {
        setBreakIn(e.target.checked);
    };

    return(
        <>
            <Typography.Title level={2}>Зміна: створення нового запису</Typography.Title>
            <Form
                labelCol={{
                    span: 8,
                }}
                wrapperCol={{
                    span: 16,
                }}
                scrollToFirstError={true}
                name="shift-registration"
                className="shift-registration">
                <Form.Item
                    label="Початок зміни" required={true}>
                    <Space>
                        <Form.Item name="shiftStartDate" noStyle required={true}>
                            <DatePicker picker="date" placeholder="Оберіть дату"/>
                        </Form.Item>
                        <Form.Item name="shiftStartTime" noStyle required={true}>
                            <DatePicker picker="time" style={{width: "70%%"}} placeholder="Оберіть час"/>
                        </Form.Item>
                    </Space>
                </Form.Item>
                <Form.Item label="Кінець зміни" required={true}>
                    <Space >
                        <Form.Item name="shiftEndDate" noStyle required={true}>
                            <DatePicker picker="date" style={{width: "70%%"}} placeholder="Оберіть дату"/>
                        </Form.Item>
                        <Form.Item name="shiftEndTime" noStyle required={true}>
                            <DatePicker picker="time" style={{width: "70%%"}} placeholder="Оберіть час"/>
                        </Form.Item>
                    </Space>
                </Form.Item>
                <Form.Item label="Перерва" required={true}>
                    <Space>
                        <Form.Item noStyle>
                            <Checkbox onChange={onBreakCheckChange} style={{width: "50%%"}} checked={breakIn}>Вираховується</Checkbox>
                        </Form.Item>
                        <Form.Item name="breakDuration" noStyle required={true}>
                            <InputNumber addonAfter="хв" style={{
                                width: 'calc(75% - 9px)'
                            }} defaultValue="0" disabled={!breakIn}/>
                        </Form.Item>
                    </Space>
                </Form.Item>
                <Form.Item name="workedHours" label="Пропрацьовано часу">
                    <span className="ant-form-text">{workedHours} годин {workedMinutes} хвилин</span>
                </Form.Item>
                <Form.Item name="ratePerHour"label="Ставка" required={true}>
                    <InputNumber addonAfter="зл/год." style={{
                        display: 'inline-block',
                        width: '78%'
                    }} defaultValue={rate} />
                </Form.Item>
                <Form.Item label="Дохід">
                    <span className="ant-form-text">{income} зл.</span>
                </Form.Item>
                <Form.Item name="company" label="Компанія">
                    <Select options={companyList} placeholder="Виберіть зі списку"/>
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
