// dllmain.h : Declaration of module class.

class CDSPModule : public CAtlDllModuleT< CDSPModule >
{
public :
	DECLARE_LIBID(LIBID_DSPLib)
	DECLARE_REGISTRY_APPID_RESOURCEID(IDR_DSP, "{AF4D52DF-B89E-49BC-BCDD-85654856B222}")
};

extern class CDSPModule _AtlModule;
