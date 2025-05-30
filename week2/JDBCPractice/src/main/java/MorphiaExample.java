import dev.morphia.Datastore;
import dev.morphia.query.updates.UpdateOperators;

import static dev.morphia.query.filters.Filters.eq;

public class MorphiaExample {
    public static void main(String[] args) {
        Datastore ds = MorphiaUtil.getDatastore();

        // CREATE
        UserDoc u = new UserDoc("alice@example.com", "Alice");
        ds.save(u);

        // READ (by filter)
        UserDoc found = ds.find(UserDoc.class)
                .filter(eq("email", "alice@example.com"))
                .first();
        System.out.println("Found: " + found.getName());

        // UPDATE
        ds.find(UserDoc.class)
                .filter(eq("email", "alice@example.com"))
                .update(UpdateOperators.set("name", "updatedalice"))
                .execute();
        System.out.println("Updated");

        // DELETE
        ds.find(UserDoc.class)
                .filter(eq("email", "alice@example.com"))
                .delete();

        /*
        try (var session = ds.getDatastore().startSession()) {
            session.startTransaction();
            try {
                // multiple operations within the same transaction:
                UserDoc txUser = new UserDoc("tx@example.com", "TxUser");
                ds.save(session, txUser);

                UserDoc txFound = ds.find(session, UserDoc.class)
                        .filter(Filters.eq("email", "tx@example.com"))
                        .first();
                System.out.println("[TX] Found: " + txFound.getName());

                // commit the transaction
                session.commitTransaction();
            } catch (Exception txEx) {
                session.abortTransaction();
                System.err.println("Transaction aborted: " + txEx.getMessage());
            }

         */
        }
    }
