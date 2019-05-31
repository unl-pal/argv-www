package mtgdeckbuilder.frontend.swingworkers;

public interface SearchProgressHarvest {

    void started(int numberOfParts);
    void partDone(int partNumber);
    void finished();
    void error();

}
