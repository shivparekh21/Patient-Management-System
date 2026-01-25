package com.pm.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// grpc service and managed by springboot lifecycle
@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);
    // Implement grpc server stub by extending the class

    // StreamObserver powerful concept in grpc
    // Lets you add multiple response
    // Back and Forth communication with client ones client makes connection to server
    // Different from standard REST http request, it uses single response model
    @Override
    public void createBillingAccount(BillingRequest billingRequest,
                                     StreamObserver<BillingResponse> responseObserver){
        log.info("createBillingAccount request received {} ", billingRequest.toString());

        // Business logic - e.g. save to database, perform calculations

        BillingResponse billingResponse = BillingResponse.newBuilder()
                .setAccountId("1234")
                .setStatus("ACTIVE")
                .build();

        // this line used to send response back from grpc service, to send more response we can use onNext()
        responseObserver.onNext(billingResponse);
        // end the cycle. complete the request.
        responseObserver.onCompleted();
    }
}
