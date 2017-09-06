package com.lyne.common.data_serialize.protobuf.tutorial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by nn_liu on 2016/12/5.
 */
public class MainDemo {
    public static void main(String[] args) throws IOException {

        // 按照定义的数据结构，创建一个Person
        AddressBookProtos.Person jhon =
                AddressBookProtos.Person.newBuilder()
                .setId(1234)
                .setName("John Doe")
                .setEmail("protocol@buffer.com")
                .addPhones(
                        AddressBookProtos.Person.PhoneNumber.newBuilder()
                        .setNumber("555-4321")
                        .setType(AddressBookProtos.Person.PhoneType.WORK)
                ).build();

        // 按照定义的数据结构，创建一个AddressBook
        AddressBookProtos.AddressBook addressBook =
                AddressBookProtos.AddressBook.newBuilder()
                .addPeople(jhon).build();

        // 将数据写到输出流，如网络输出流，这里就用ByteArrayOutputStream来代替
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        addressBook.writeTo(output);

        // -------------- 分割线：上面是发送方，将数据序列化后发送 ---------------

        byte[] byteArray = output.toByteArray();

        // -------------- 分割线：下面是接收方，将数据接收后反序列化 ---------------

        // 接收到流并读取，如网络输入流，这里用ByteArrayInputStream来代替
        ByteArrayInputStream input = new ByteArrayInputStream(byteArray);

        // 反序列化
        AddressBookProtos.AddressBook addressBookDeserialize = AddressBookProtos.AddressBook.parseFrom(input);
        List<AddressBookProtos.Person> persons = addressBookDeserialize.getPeopleList();
        for(AddressBookProtos.Person person : persons) {
            System.out.println(person.getName());
            System.out.println(person.getEmail());
            System.out.println(person.getPhones(0).getNumber());
            System.out.println(person.getPhones(0).getType());
        }
    }
}
