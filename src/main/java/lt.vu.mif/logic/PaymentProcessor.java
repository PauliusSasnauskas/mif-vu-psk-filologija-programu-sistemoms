package lt.vu.mif.logic;

import lt.vu.mif.entities.Parcel;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

@SessionScoped
public class PaymentProcessor implements Serializable {
    @Inject
    private PaymentProcessingStrategy processingStrategy;

    private CompletableFuture<Parcel> processingTask = null;

    public void process(Parcel p){
        if (isProcessingRunning()){
            return;
        }
        processingTask = CompletableFuture.supplyAsync(() -> processingStrategy.processParcelPayment(p));

    }

    public boolean isProcessingRunning(){
        return !(processingTask == null || processingTask.isDone());
    }

}
