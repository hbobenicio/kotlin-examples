package br.com.hbobenicio.sslrexamples.sslrjsonparser

import com.sonar.sslr.impl.Parser
import org.sonar.colorizer.Tokenizer
import org.sonar.sslr.grammar.GrammarRuleKey
import org.sonar.sslr.toolkit.AbstractConfigurationModel
import org.sonar.sslr.toolkit.ConfigurationProperty
import org.sonar.sslr.toolkit.Toolkit
import org.sonar.sslr.toolkit.ValidationCallback
import java.nio.charset.Charset
import java.nio.charset.IllegalCharsetNameException
import java.nio.charset.UnsupportedCharsetException

fun getGrammarRuleByString(name: String?): GrammarRuleKey? = JsonGrammarRules.values()
    .firstOrNull { it.name == name }

fun main() {
    val toolkit = Toolkit("hbobenicio : MyLanguage : Toolkit", MyConfigurationModel())
    toolkit.run()
}

class MyConfigurationModel : AbstractConfigurationModel() {
    private val charsetProperty = ConfigurationProperty("Charset", "Charset used when opening files.", "UTF-8",
        ValidationCallback { newValueCandidate ->
            try {
                Charset.forName(newValueCandidate)
                return@ValidationCallback ""
            } catch (e: IllegalCharsetNameException) {
                return@ValidationCallback "Illegal charset name: $newValueCandidate"
            } catch (e: UnsupportedCharsetException) {
                return@ValidationCallback "Unsupported charset: $newValueCandidate"
            }
        })

    private val rootRuleProperty = ConfigurationProperty("Root Rule",
        "Root rule from grammar used to validate the code. Empty for default value.",
        "",
        ValidationCallback { newValueCandidate ->
            if (newValueCandidate === "") return@ValidationCallback ""
            try {
                JsonGrammarRules.valueOf(newValueCandidate)
                //.forName(newValueCandidate);
                return@ValidationCallback ""
            } catch (e: IllegalArgumentException) {
                return@ValidationCallback "Grammar has no rule with the specified name: $newValueCandidate"
            } catch (e: NullPointerException) {
                return@ValidationCallback "Unsupported charset: $newValueCandidate"
            }
        })

    override fun getProperties(): List<ConfigurationProperty> = listOf(charsetProperty, rootRuleProperty)

    override fun getCharset(): Charset = Charset.forName(charsetProperty.value)

    override fun doGetParser(): Parser<*> {
        updateConfiguration()
        val p: Parser<*> = createJSONParser()
        if (rootRuleProperty.value != "") {
            val g = p.grammar
            val rule = getGrammarRuleByString(rootRuleProperty.value)
            if (rule != null) {
                p.setRootRule(g.rule(rule))
                println("Setting Parser root rule to " + rootRuleProperty.value)
            }
        }
        return p
    }

    override fun doGetTokenizers(): List<Tokenizer> {
        updateConfiguration()
        return JsonColorizer.tokenizers
    }

    companion object {
        private fun updateConfiguration() {
            /* Construct a parser configuration object from the properties */
        }
    }
}