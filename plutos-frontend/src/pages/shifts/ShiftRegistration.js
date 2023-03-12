import {Form, Typography} from "antd";

export default function ShiftRegistration() {

    return(
        <>
            <Typography.Title level={2}>Зміна: створення нового запису</Typography.Title>
            <Form name="shift-registration"
                  labelCol={{
                      span: 8,
                  }}
                  wrapperCol={{
                      span:16
                  }}

            >

            </Form>
        </>
    )
}