package com.demos.henrique.waldophotos.Networking.Serialization;




public class GraphQLTools {

    public static String queryBuilderForAlbum(String albumId, int offSet, int limit)
    {

        String graphQlQuery = "query {\n" +
                "  album(id: \""+albumId+"\") {\n" +
                "    id\n" +
                "    name\n" +
                "    photos(slice: { limit: "+limit+", offset: "+offSet+" }) {\n" +
                "      records {\n" +
                "        id\n" +
                "        urls {\n" +
                "          size_code\n" +
                "          url\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";


        return graphQlQuery;
    }
}
