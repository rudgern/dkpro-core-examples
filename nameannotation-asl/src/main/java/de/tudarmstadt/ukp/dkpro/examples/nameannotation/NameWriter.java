package de.tudarmstadt.ukp.dkpro.examples.nameannotation;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.examples.type.Name;

public class NameWriter extends JCasConsumer_ImplBase {

    @Override
    public void process(final JCas aJCas) throws AnalysisEngineProcessException {
        for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {
            for (Name ne : JCasUtil.selectCovered(aJCas, Name.class, sentence)) {
                System.out.println(ne.getCoveredText());
            }
        }
    }
}