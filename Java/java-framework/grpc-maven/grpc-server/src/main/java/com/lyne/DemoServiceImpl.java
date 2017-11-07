package com.lyne;

import com.lyne.grpc.study.dto.*;
import com.lyne.grpc.study.service.DemoServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nn_liu
 * @Created 2017-11-07-10:56
 */

public class DemoServiceImpl extends DemoServiceGrpc.DemoServiceImplBase {

    public void ping(PingRequest pingRequest, StreamObserver<PingResponse> streamObserver) {
        PingResponse reply = PingResponse.newBuilder().setOut("pong => " + pingRequest.getIn()).build();
        streamObserver.onNext(reply);
        streamObserver.onCompleted();
    }

    public void getPersonList(QueryParameter queryParameter, StreamObserver<PersonList> streamObserver) {
        //System.out.println(queryParameter.getAgeStart() + "-" + queryParameter.getAgeEnd());
        PersonList.Builder personListBuilder = PersonList.newBuilder();
        Person.Builder builder = Person.newBuilder();
        List<Person> list = new ArrayList<Person>();
        for (short i = 0; i < 10; i++) {
            list.add(builder.setAge(i).setChildrenCount(i).setName("test" + i).setSex(true).build());
        }
        personListBuilder.addAllItems(list);
        streamObserver.onNext(personListBuilder.build());
        streamObserver.onCompleted();
    }

}
