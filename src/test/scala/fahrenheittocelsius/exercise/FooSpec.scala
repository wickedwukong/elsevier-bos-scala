package fahrenheittocelsius.exercise

import com.elsevier.bos.fahrenheittocelsius.exercise.Foo
import org.specs2.mutable.Specification

class FooSpec extends Specification {
  "Foo" should {
    "bar" in {
      new Foo().bar must_== ("bar")
    }
  }
}
