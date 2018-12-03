{
  "$schema": "http://json-schema.org/draft-07/schema#",
  "description": "An input to fibonacci function.",
  "type": "object",
  "javaInterfaces": ["io.radanalytics.operator.common.EntityInfo"],
  "properties": {
    "name": {
      "type": "string"
    },
    "input": {
      "type": "integer",
      "default": "1"
    }
  },
  "required": [ ]
}