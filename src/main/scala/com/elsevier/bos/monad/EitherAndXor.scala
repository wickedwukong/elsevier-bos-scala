package com.elsevier.bos.monad

case class Username(value: String)

sealed trait Error

case class AuthenticationFailed(message: String) extends Error
case class InvalidDocument(documentId: DocumentId, message: String) extends Error

trait Id {
  val value: String
}

case class UserId(value: String) extends Id

case class User(userId: UserId, name: String)

case class DocumentId(value: String) extends Id

case class Document(documentId: DocumentId, title: String)

object ScalazEitherDemo extends App {

  import scalaz._

  private def documents = Map(DocumentId("1") -> "Functional programming in Scala", DocumentId("2") -> "Pragmatic Programmer")


  private def findDocument(documentId: DocumentId): Option[Document] = {
    documents.get(documentId).map {
      Document(documentId, _)
    }
  }

  private def authenticate(userId: UserId, secret: String): \/[Error, User] = {
    if (userId.value != "12345") {
      -\/(AuthenticationFailed("Your username or password is invalid"))
    } else {
      \/-(User(userId, "Bob"))
    }
  }


  def findDocument(userId: UserId, secret: String, documentId: DocumentId): Error \/ Document = {
    import scalaz._
    import syntax.std.option._

    for {
      _ <- authenticate(userId, secret)
      document <- findDocument(documentId).toRightDisjunction(InvalidDocument(documentId, s"No document found for document Id: ${documentId.value}"))
    } yield document
  }

  println(findDocument(UserId("12345"), "let me in", DocumentId("1")))
  println(findDocument(UserId("99999"), "do not let me in", DocumentId("1")))
  println(findDocument(UserId("12345"), "let me in", DocumentId("100")))

}
