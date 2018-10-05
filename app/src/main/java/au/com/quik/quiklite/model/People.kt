package au.com.quik.quiklite.model

class People (val name : String = String(),
              val age  : String = String(),
              val rating : String = String(),
              val category: String = String()){
    var id : Long = Long.MIN_VALUE
}