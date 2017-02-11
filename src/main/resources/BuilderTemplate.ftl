
public class ${domain}DomainBuilder extends ${builderBaseClass}<${domain}Domain, ${domain}DomainBuilder> {

	// private member
	<#list members as m>
	/** ${m.explain} */
	private ${m.type} ${m.name};

    </#list>
	
	// protected method
	@Override
	protected void apply(${domain}Domain domain, ${domain}DomainBuilder builder) {
		<#list members as m>
		builder.with${m.name?cap_first}(domain.get${m.name?cap_first}());
		</#list>
	}

	@Override
	protected ${domain}Domain createDomainObject() {
		return new ${domain}Domain(${memberListWithComma});
	}

	@Override
	protected ${domain}DomainBuilder getThis() {
		return this;
	}

	@Override
	protected ${domain}DomainBuilder newInstance() {
		return new ${domain}DomainBuilder();
	}

	// public method
	<#list members as m>
	/**
	 * {@link ${domain}Domain}に与える${m.explain}をこのビルダに設定する。
	 *
	 * @param ${m.name}
	 *            ${m.explain}
	 * @return {@link ${domain}DomainBuilder}
	 */
	public ${domain}DomainBuilder with${m.name?cap_first}(final ${m.type} ${m.name}) {
		addConfigurator(new BuilderConfigurator<${domain}DomainBuilder>() {
			@Override
			public void configure(${domain}DomainBuilder builder) {
				builder.${m.name} = ${m.name};
			}
		});
		return getThis();
	}
	
	</#list>
}