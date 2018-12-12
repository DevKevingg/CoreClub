package me.devkevin.core.utils.finalutil;

import me.devkevin.core.CorePlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.*;
import org.json.simple.parser.*;
import java.net.*;
import org.bukkit.scheduler.*;
import java.io.*;
import org.json.simple.*;
import java.util.*;
import java.util.UUID;

public final class UUIDUtil
{
    private static final String PROFILE_URL = "https://api.mojang.com/profiles/minecraft";
    private static final Map<String, UUID> CACHE;
    private static final JSONParser PARSER;

    private UUIDUtil() {
        throw new RuntimeException("Cannot instantiate a utility class.");
    }

    private static void writeBody(final HttpURLConnection connection, final String body) throws Exception {
        final OutputStream stream = connection.getOutputStream();
        stream.write(body.getBytes());
        stream.flush();
        stream.close();
    }

    private static HttpURLConnection createConnection() throws Exception {
        final URL url = new URL("https://api.mojang.com/profiles/minecraft");
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }

    private static UUID getUUID(final String id) {
        return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
    }

    public static UUID getUUIDFromName(final String nig) {
        if (UUIDUtil.CACHE.containsKey(nig)) {
            return UUIDUtil.CACHE.get(nig);
        }
        return new AsyncUUIDFetcher(nig).getUniqueID();
    }

    static {
        CACHE = new HashMap<String, UUID>();
        PARSER = new JSONParser();
    }

    private static class AsyncUUIDFetcher
    {
        private UUID uniqueID;

        private AsyncUUIDFetcher(final String nig) {
            new BukkitRunnable () {
                public void run() {
                    try {
                        final HttpURLConnection connection = createConnection();
                        final String body = JSONArray.toJSONString((List)Collections.singletonList(nig));
                        writeBody(connection, body);
                        final JSONArray array = (JSONArray)UUIDUtil.PARSER.parse((Reader)new InputStreamReader(connection.getInputStream()));
                        for (final Object profile : array) {
                            final JSONObject jsonProfile = (JSONObject)profile;
                            final String id = (String)jsonProfile.get((Object)"id");
                            AsyncUUIDFetcher.this.uniqueID = getUUID(id);
                            UUIDUtil.CACHE.put(nig, AsyncUUIDFetcher.this.uniqueID);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskAsynchronously((Plugin)CorePlugin.getInstance());
        }

        public UUID getUniqueID() {
            return this.uniqueID;
        }
    }
}

