package back.ecommerce.api.support

import org.springframework.restdocs.payload.JsonFieldType

sealed class DocsFieldType(
    val type: JsonFieldType,
)
data object ARRAY : DocsFieldType(JsonFieldType.ARRAY)
data object BOOLEAN : DocsFieldType(JsonFieldType.BOOLEAN)
data object NUMBER : DocsFieldType(JsonFieldType.NUMBER)
data object STRING : DocsFieldType(JsonFieldType.STRING)
data object NULL : DocsFieldType(JsonFieldType.NULL)
data object ANY : DocsFieldType(JsonFieldType.VARIES)
data object DATE : DocsFieldType(JsonFieldType.STRING)
data object DATETIME : DocsFieldType(JsonFieldType.STRING)



