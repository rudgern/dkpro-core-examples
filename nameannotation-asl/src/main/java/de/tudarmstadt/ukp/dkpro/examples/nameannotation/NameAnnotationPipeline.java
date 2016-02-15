/*******************************************************************************
 * Copyright 2010
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tudarmstadt.ukp.dkpro.examples.nameannotation;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.dictionaryannotator.DictionaryAnnotator;
import de.tudarmstadt.ukp.dkpro.core.examples.type.Name;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;

/**
 * This pipeline uses the custom annotation type {@link Name} to annotate names in the input text.
 * The {@link DictionaryAnnotator} looks up names from a static names file and annotates them in the
 * documents.
 * <p>
 * The output is written to the target directory. Use {@code NameAnnotationPipelineTest} in the test directory to check
 * the output.
 * </p>
 */
public class NameAnnotationPipeline
{
    public static void main(final String[] args)
            throws IOException, UIMAException
    {
        CollectionReaderDescription reader = createReaderDescription(
                TikaReader.class,
                TikaReader.PARAM_SOURCE_LOCATION, "src/main/resources/namedEntitiesTexts",
                TikaReader.PARAM_PATTERNS, "*",
                TikaReader.PARAM_LANGUAGE, "en");

        AnalysisEngineDescription tokenizer = createEngineDescription(
                BreakIteratorSegmenter.class);

        AnalysisEngineDescription nameFinder = createEngineDescription(
                DictionaryAnnotator.class,
                DictionaryAnnotator.PARAM_MODEL_LOCATION,
                "src/main/resources/dictionaries/names.txt",
                DictionaryAnnotator.PARAM_ANNOTATION_TYPE, Name.class);

        AnalysisEngineDescription writer = createEngineDescription(NameWriter.class);

        SimplePipeline.runPipeline(reader, tokenizer, nameFinder, writer);
    }
}
