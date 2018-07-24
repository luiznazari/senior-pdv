package desafio.senior.pdv.utils;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

public class NumeroUtilsTest {
	
	@Test
	public void deveArredondarValorEConverterParaDouble() throws Exception {
		assertDouble(1.5, NumeroUtils.arredondar(1.5));
		assertDouble(1.25, NumeroUtils.arredondar(1.25));
		assertDouble(0.96, NumeroUtils.arredondar(0.9555));
		assertDouble(0.0, NumeroUtils.arredondar(( Double ) null));
		assertDouble(0.0, NumeroUtils.arredondar(Double.NaN));
		assertDouble(0.0, NumeroUtils.arredondar(100.0 / 0.0)); // Infinity
		assertDouble(813478.64, NumeroUtils.arredondar(813478.6432));
		assertDouble(129.30, NumeroUtils.arredondar(129.298));
	}
	
	@Test
	public void deveConverterNumeroParaFormatoDecimalBr() throws Exception {
		assertEquals("0,00", NumeroUtils.formatarBr(( Double ) null));
		assertEquals("0,00", NumeroUtils.formatarBr(0));
		assertEquals("0,00", NumeroUtils.formatarBr(.0));
		assertEquals("9,90", NumeroUtils.formatarBr(9.9));
		assertEquals("9,09", NumeroUtils.formatarBr(9.09));
		assertEquals("9.999.999,99", NumeroUtils.formatarBr(9999999.99));
	}
	
	private void assertDouble(Double expected, Double number) {
		assertEquals(expected, number, NumberUtils.DOUBLE_ZERO);
	}
	
}
