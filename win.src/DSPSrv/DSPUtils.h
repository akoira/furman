// DSPUtils.h : Declaration of the CDSPUtils

#pragma once
#include "resource.h"       // main symbols

#include "DSPSrv.h"


#if defined(_WIN32_WCE) && !defined(_CE_DCOM) && !defined(_CE_ALLOW_SINGLE_THREADED_OBJECTS_IN_MTA)
#error "Single-threaded COM objects are not properly supported on Windows CE platform, such as the Windows Mobile platforms that do not include full DCOM support. Define _CE_ALLOW_SINGLE_THREADED_OBJECTS_IN_MTA to force ATL to support creating single-thread COM object's and allow use of it's single-threaded COM object implementations. The threading model in your rgs file was set to 'Free' as that is the only threading model supported in non DCOM Windows CE platforms."
#endif



// CDSPUtils

class ATL_NO_VTABLE CDSPUtils :
	public CComObjectRootEx<CComSingleThreadModel>,
	public CComCoClass<CDSPUtils, &CLSID_DSPUtils>,
	public IDispatchImpl<IDSPUtils, &IID_IDSPUtils, &LIBID_IMDSPSrvLib, /*wMajor =*/ 1, /*wMinor =*/ 0>
{
public:
	CDSPUtils()
	{
	}

DECLARE_REGISTRY_RESOURCEID(IDR_DSPUTILS)


BEGIN_COM_MAP(CDSPUtils)
	COM_INTERFACE_ENTRY(IDSPUtils)
	COM_INTERFACE_ENTRY(IDispatch)
END_COM_MAP()



	DECLARE_PROTECT_FINAL_CONSTRUCT()

	HRESULT FinalConstruct()
	{
		return S_OK;
	}

	void FinalRelease()
	{
	}

public:

	STDMETHOD(GetData)(BSTR DWGFullPath, DOUBLE* LineLength, DOUBLE* CurveLength, DOUBLE* Square, LONG* RegionCount);
};

OBJECT_ENTRY_AUTO(__uuidof(DSPUtils), CDSPUtils)
