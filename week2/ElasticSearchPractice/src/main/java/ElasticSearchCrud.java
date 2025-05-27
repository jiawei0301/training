import org.apache.http.HttpHost;
import org.elasticsearch.client.*;
import org.elasticsearch.action.index.*;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.update.*;
import org.elasticsearch.action.delete.*;
import java.util.*;

public class ElasticSearchCrud {
    public static void main(String[] args) throws Exception {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        String index = "users", id = "1";

        // Create
        Map<String, Object> data = new HashMap<>();
        data.put("name", "Charlie");
        data.put("age", 28);
        client.index(new IndexRequest(index).id(id).source(data), RequestOptions.DEFAULT);

        // Read
        GetResponse response = client.get(new GetRequest(index, id), RequestOptions.DEFAULT);
        System.out.println("Read: " + response.getSourceAsString());

        // Update
        client.update(new UpdateRequest(index, id).doc("age", 29), RequestOptions.DEFAULT);

        // Delete
        client.delete(new DeleteRequest(index, id), RequestOptions.DEFAULT);

        client.close();
    }
}