package mhfc.net.common.util;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import net.minecraft.util.ResourceLocation;

public class UtilitiesTest {

	ResourceLocation location = new ResourceLocation("mhfc:lang/en_US.lang");
	ResourceLocation location_wrong = new ResourceLocation("mhfc:not_there/en_US.lang");

	@Test
	public void test() throws IOException {
		InputStream stream = Utilities.openEmbeddedResource(location);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String firstLine = reader.readLine();
		assertTrue(firstLine.matches(".*?=.*"));
	}

	@Test(expected = IOException.class)
	public void testWrongFile() throws IOException {
		InputStream stream = Utilities.openEmbeddedResource(location_wrong);
		assertTrue(stream == null);
	}

}
