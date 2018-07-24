package desafio.senior.pdv.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Function;

public abstract class NumeroUtils {
	
	private static final Locale PT_BR = new Locale("pt", "br");
	public static final Function<Number, Double> toDouble = numero -> arredondar(numero.doubleValue());
	public static final Function<Number, Double> toDoubleSemArredondar = Number::doubleValue;
	
	/**
	 * Arredonda um {@link Double} para somente duas casas após a vírgula.
	 * 
	 * @param valor
	 * @return {@link Double} com duas casas após a vírgula.
	 * @see NumberFormat#arredondaDuasCasas(Number, Function)
	 */
	public static Double arredondar(Double valor) {
		return arredondar(valor, toDoubleSemArredondar);
	}
	
	/**
	 * Arredonda um número para somente duas casas após a vírgula. Caso a fração restante seja 0.005,
	 * arredonda pra 0.01, caso contrário arredonda pra 0.00.
	 * 
	 * @param valor
	 * @return valor com duas casas após a virgula
	 * @param numberFunction
	 *            função para converter o valor resultante
	 * @see RoundingMode#HALF_UP
	 */
	public static <N extends Number> N arredondar(Number valor, Function<Number, N> numberFunction) {
		BigDecimal valorArredondado;
		if (valor == null || Double.isNaN(valor.doubleValue()) || Double.isInfinite(valor.doubleValue())) {
			valorArredondado = BigDecimal.ZERO;
		} else {
			valorArredondado = BigDecimal.valueOf(valor.doubleValue());
		}
		return numberFunction.apply(valorArredondado.setScale(2, RoundingMode.HALF_UP));
	}
	
	/**
	 * Formata o número para formato brasileiro: 0.000,00.
	 * 
	 * @param number
	 * @return string representando o valor do número.
	 */
	public static String formatarBr(Object number) {
		if (number == null) {
			number = 0;
		}
		java.text.NumberFormat formatter = DecimalFormat.getNumberInstance(PT_BR);
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		return formatter.format(number);
	}
	
	private NumeroUtils() {}
	
}
