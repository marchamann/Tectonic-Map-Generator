package ca.hamann.mapgen.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for ca.hamann.mapgen.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestPlateGenerator.class);
		suite.addTestSuite(TestMapConfigurationParser.class);
		suite.addTestSuite(TestCoastInlandDivider.class);
		suite.addTestSuite(TestNonLinearMapStorage.class);
		suite.addTestSuite(TestDirectionSubsequenceWriter.class);
		suite.addTestSuite(TestPlateDrifter.class);
		suite.addTestSuite(TestNeighbourhoods.class);
		suite.addTestSuite(TestDirectionSequence.class);
		suite.addTestSuite(TestLattitudeDrifter.class);
		suite.addTestSuite(TestNextCommaParser.class);
		suite.addTestSuite(TestBufferedNextCommaReader.class);
		suite.addTestSuite(TestTectonicValues.class);
		suite.addTestSuite(TestSinusoidalProjection.class);
		suite.addTestSuite(TestPlateSplitter.class);
		suite.addTestSuite(TestSeaDrainageDivider.class);
		suite.addTestSuite(TestBasicMover.class);
		suite.addTestSuite(TestNameValuePairParser.class);
		suite.addTestSuite(TestLakeFiller.class);
		suite.addTestSuite(TestSequenceJoiner.class);
		suite.addTestSuite(TestLandSeaDivider.class);
		suite.addTestSuite(TestTectonicMapReader.class);
		suite.addTestSuite(TestPlateParser.class);
		suite.addTestSuite(TestJsonObjectParser.class);
		suite.addTestSuite(TestRiverMaker.class);
		suite.addTestSuite(TestSubductionRelation.class);
		suite.addTestSuite(TestMapConfigurationWriter.class);
		suite.addTestSuite(TestDirectionSubSequenceParser.class);
		suite.addTestSuite(TestPlateSerializer.class);
		suite.addTestSuite(TestSinusoidalMap.class);
		suite.addTestSuite(TestDrainageProcessor.class);
		suite.addTestSuite(TestJsonArrayParser.class);
		suite.addTestSuite(TestRotater.class);
		suite.addTestSuite(TestPlateWriter.class);
		suite.addTestSuite(TestSinusoidalLocation.class);
		suite.addTestSuite(TestSequenceFiller.class);
		suite.addTestSuite(TestJsonArrayWriter.class);
		suite.addTestSuite(TestLocationWriter.class);
		suite.addTestSuite(TestEroder.class);
		suite.addTestSuite(TestClosureDivider.class);
		suite.addTestSuite(TestSinusoidalMapStorage.class);
		suite.addTestSuite(TestDrainageGraph.class);
		suite.addTestSuite(TestTectonicPlates.class);
		suite.addTestSuite(TestCylindricalProjection.class);
		//$JUnit-END$
		return suite;
	}
}
