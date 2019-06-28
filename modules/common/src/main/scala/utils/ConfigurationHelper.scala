package utils

import play.api.{ConfigLoader, Configuration}

object ConfigurationHelper {
  implicit class CountryConfiguration(configuration: Configuration) {
    def getForCountry[A](country: String, path: String, fallbackToDefault: Boolean)
                        (implicit loader: ConfigLoader[A]): A = {
      configuration.getOptional[A](s"$country.$path") match {
        case Some(value)                =>
          value
        case None if fallbackToDefault  =>
          configuration.get[A](path)
        case None if !fallbackToDefault =>
          throw new RuntimeException(s"$country.$path key not found in configuration")
      }
    }

    def getOptionalForCountry[A](country: String, path: String, fallbackToDefault: Boolean = true)
                                (implicit loader: ConfigLoader[A]): Option[A] = {
      configuration.getOptional[A](s"$country.$path") match {
        case Some(config)              => Some(config)
        case None if fallbackToDefault => configuration.getOptional[A](path)
        case _                         => None
      }
    }
  }
}
